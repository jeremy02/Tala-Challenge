/*
* Copyright 2021 Jeremiah Thuku (https://github.com/jeremy02)
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.example.myapplication.data.usecases

import com.example.myapplication.data.repository.TalaRepository
import javax.inject.Inject

/**
 * A use-case to load all the user loans from Github URL.
 * @author Jeremiah Thuku
 */
class FetchUserLoansUseCase @Inject constructor(
    private val repository: TalaRepository
    ) {
    suspend operator fun invoke() = repository.loadAllLoans()
}
