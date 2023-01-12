package com.peterchege.pinstagram.core.core_datastore

import androidx.datastore.core.Serializer
import com.peterchege.pinstagram.core.core_model.external_models.User
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object UserInfoSerializer : Serializer<User?> {

    override val defaultValue: User?
        get() = null

    override suspend fun readFrom(input: InputStream): User? {
        return try {
            Json.decodeFromString(
                deserializer = User.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: User?, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = User.serializer(),
                value = t!!
            ).encodeToByteArray()
        )
    }
}