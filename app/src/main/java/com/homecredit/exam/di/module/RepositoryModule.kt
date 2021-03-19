package com.homecredit.exam.di.module

import com.homecredit.exam.data.repository.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        MainRepository(get(),get())
    }
}
