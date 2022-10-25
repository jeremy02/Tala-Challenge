package com.example.myapplication.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.responses.Locales
import com.example.myapplication.data.remote.responses.UserLoan
import com.example.myapplication.data.usecases.FetchLocalesUseCase
import com.example.myapplication.data.usecases.FetchUserLoansUseCase
import com.wajahatkarim3.imagine.data.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchUserLoansUseCase: FetchUserLoansUseCase,
    private val fetchLocalesUseCase: FetchLocalesUseCase
) : ViewModel() {
    private var _TAG = HomeViewModel::class.simpleName

    private var _uiState = MutableLiveData<HomeUiState>()
    var uiStateLiveData: LiveData<HomeUiState> = _uiState


    // To get or load or Locales
    private var _userLocalesList = MutableLiveData<Locales>()
    var userLocalesListLiveData: LiveData<Locales> = _userLocalesList

    // To get User Loans List
    private var _userLoansList = MutableLiveData<List<UserLoan>>()
    var userLoansListLiveData: LiveData<List<UserLoan>> = _userLoansList

    private var _userLoansPaginatedList = MutableLiveData<List<UserLoan>>()
    var userLoansPaginatedListLiveData: LiveData<List<UserLoan>> = _userLoansPaginatedList

    // will be used for pagination or simulate pagination
    private var pageNumber = 1
    private var itemsPageLimit = 15
    var fromIndex: Int = 0
    var toIndex: Int = itemsPageLimit - 1

    init {
        // Get or Load all the Loans for all Users
        fetchAllLoans(pageNumber)
    }

    fun loadMoreUserLoansData() {
        pageNumber++
        fetchAllLoans(pageNumber)
    }

    fun retryLoadLoans() {
        pageNumber = 1
        fetchAllLoans(pageNumber)
    }

    // Get or Load all the Locales
    fun fetchAllLocales(context: Context) {
        _uiState.postValue(LoadingState)

        viewModelScope.launch {
            fetchLocalesUseCase(context).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        // _uiState.postValue(ContentState)

                        // Add a new key with value to the data
                        // TODO If this was a list it would be easier and wouldn't need below
                        val localesDataResult = dataState.data
                        if(localesDataResult.ke != null) {
                            localesDataResult.ke?.localeCode = "ke"
                        }

                        if(localesDataResult.mx != null) {
                            localesDataResult.mx?.localeCode = "mx"
                        }

                        if(localesDataResult.ph != null) {
                            localesDataResult.ph?.localeCode = "ph"
                        }

                        _userLocalesList.postValue(localesDataResult)
                    }
                    is DataState.Error -> {
                        _uiState.postValue(ErrorState(dataState.message))
                    }
                }
            }
        }
    }

    // Get or Load all the Loans for all Users
    fun fetchAllLoans(page: Int) {
        _uiState.postValue(if (page == 1) LoadingState else LoadingNextPageState)

        // Since Our Api or data has no support for pagination, we are going to simulate pagination
        // By Loading all the data from the API endpoint initially and create a function that
        // Simulates pagination, On Page = 1 load all the data otherwise paginate the loaded data
        // By using the Sublist function
        if(page == 1) {
            viewModelScope.launch {
                fetchUserLoansUseCase().collect { dataState ->
                    when (dataState) {
                        is DataState.Success -> {

                            _uiState.postValue(ContentState)

                            // Add the Locales Data to the User Loans List
                            val userLoansListResult = addLocalesDataToUserLoansList(dataState.data)

                            // This will be used to store the full list since we need the length later for pagination
                            _userLoansList.postValue(dataState.data)
                            // Since its the first page let's using the initial fromIndex and toIndex
                            _userLoansPaginatedList.postValue(userLoansListResult.subList(fromIndex, toIndex))
                        }

                        is DataState.Error -> {
                            _uiState.postValue(ErrorState(dataState.message))
                        }
                    }
                }
            }
        } else {
            // get the new fromIndex or starting index of the list
            fromIndex = (page - 1) * itemsPageLimit
            toIndex = page * itemsPageLimit - 1

            viewModelScope.launch {
                // Add a Delay for simulation of loading scroll'
                delay(2500)

                // Add the initial Data from {_userLoansPaginatedList} MutableLiveData
                val currentUserLoansList = arrayListOf<UserLoan>()
                _userLoansPaginatedList.value?.let {
                    currentUserLoansList.addAll(it)
                }

                // Get the full list of user loans data from {_userLoansPaginatedList} MutableLiveData
                // to calculate new sublist to add to {currentUserLoansList}
                val allUserLoansList = arrayListOf<UserLoan>()
                _userLoansList.value?.let {
                    allUserLoansList.addAll(it)
                }

                // Load More data from  allUserLoansList using the new calculated
                // values of {fromIndex} and {toIndex} and add it to {currentUserLoansList}
                currentUserLoansList.addAll(allUserLoansList.subList(fromIndex, toIndex))

                // add the results to {_userLoansPaginatedList} MutableLiveData
                _uiState.postValue(ContentNextPageState)
                _userLoansPaginatedList.postValue(currentUserLoansList)
            }
        }
    }

    private fun addLocalesDataToUserLoansList(userLoansData: List<UserLoan>): List<UserLoan> {
        // Sort the data first
        userLoansData.sortedWith(compareBy { it.timestamp })

        // Get the list of locales from _userLocalesList MutableLiveData
        var localesList = Locales()
        _userLocalesList.value?.let {
            localesList = it
        }

        // add the locales to the user Loans list of odata objects
        // TODO If this was a list it would be easier
        userLoansData.map {
            if (it.locale.equals(localesList.ke?.localeCode, ignoreCase = true)) {
                it.userLoanLocale = localesList.ke
            }
            else if (it.locale.equals(localesList.mx?.localeCode, ignoreCase = true)) {
                it.userLoanLocale = localesList.mx
            }
            else if (it.locale.equals(localesList.ph?.localeCode, ignoreCase = true)) {
                it.userLoanLocale = localesList.ph
            }
            else {
                it
            }
        }

        return userLoansData
    }
}
