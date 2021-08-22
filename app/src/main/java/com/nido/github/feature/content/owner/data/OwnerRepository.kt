package com.nido.github.feature.content.owner.data

import com.nido.github.data.resultLiveDataPersistent
import javax.inject.Inject

class OwnerRepository @Inject constructor(
    private val dataSource: OwnerDataSource,
    private val dao: OwnerDao
) {

    fun getOwner(username: String) = resultLiveDataPersistent(
        networkCall = { dataSource.getOwner(username) },
        saveLocal = { dao.insertOwner(OwnerDto.fromResponse(it)) },
        observeLocal = { dao.getOwner(username) }
    )
}