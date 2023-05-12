package pl.kcieslar.wyjazdyosp.data.firebaserepo

import pl.kcieslar.wyjazdyosp.model.Forces

interface FirebaseForcesResponse {
    val list: List<Forces>?
    val exception: Exception?
}