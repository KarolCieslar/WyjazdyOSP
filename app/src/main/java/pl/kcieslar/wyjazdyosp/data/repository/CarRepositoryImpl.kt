package pl.kcieslar.wyjazdyosp.data.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.CarResponse
import pl.kcieslar.wyjazdyosp.domain.repository.CarRepository
import pl.kcieslar.wyjazdyosp.model.Car
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor() : CarRepository {

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val forcesRef: DatabaseReference = rootRef.child("forces/cars")

    override suspend fun getCars(): CarResponse {
        val response = CarResponse()
        try {
            response.list = forcesRef.get().await().children.map { snapShot ->
                val item = snapShot.getValue(Car::class.java)!!
                item.key = snapShot.key!!
                item
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }

    override suspend fun addCar(car: Car) : FirebaseCallResponse {
         return try {
            forcesRef.child(car.key).setValue(car).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }

    override suspend fun editCar(car: Car) : FirebaseCallResponse {
        return try {
            forcesRef.child(car.key).setValue(car).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }

    override suspend fun removeCar(car: Car): FirebaseCallResponse {
        return try {
            forcesRef.child(car.key).setValue(null).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }
}