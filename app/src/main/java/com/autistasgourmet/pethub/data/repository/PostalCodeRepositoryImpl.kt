package com.autistasgourmet.pethub.data.repository

import android.content.Context
import com.autistasgourmet.pethub.domain.model.ZacatecasMunicipality
import com.autistasgourmet.pethub.domain.repository.PostalCodeRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostalCodeRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : PostalCodeRepository {

    private val codeToMunicipality: Map<String, ZacatecasMunicipality>
    private val municipalityToPostalCodes: Map<ZacatecasMunicipality, List<String>>

    init {
        // carga el json una vez para crear el repositorio
        val jsonString = context.assets.open("zacatecas_postal_codes.json").bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<Map<String, List<String>>>() {}.type
        val rawMap: Map<String, List<String>> = Gson().fromJson(jsonString, type)

        // mapa con: codigo postal -> municipio
        val tempCodeToMun = mutableMapOf<String, ZacatecasMunicipality>()
        rawMap.forEach { (municipioName, zipCodes) ->
            val municipio = ZacatecasMunicipality.entries.find { it.displayName == municipioName }
            if (municipio != null) {
                zipCodes.forEach { cp ->
                    tempCodeToMun[cp] = municipio
                }
            }
        }
        codeToMunicipality = tempCodeToMun

        // mapa con: municipio -> lista de sus codigos postales
        val tempMunToCode = mutableMapOf<ZacatecasMunicipality, List<String>>()
        rawMap.forEach { (municipioName, postalCodes) ->
            val municipio = ZacatecasMunicipality.entries.find { it.displayName == municipioName }
            if (municipio != null) {
                tempMunToCode[municipio] = postalCodes
            }
        }
        municipalityToPostalCodes = tempMunToCode
    }

    override fun getMunicipalityForPostalCode(postalCode: String): ZacatecasMunicipality? {
        return codeToMunicipality[postalCode]
    }

    override fun getPostalCodesForMunicipality(municipality: ZacatecasMunicipality): List<String> {
        return municipalityToPostalCodes[municipality] ?: emptyList()
    }

    override fun isValidPostalCode(postalCode: String): Boolean {
        return codeToMunicipality.containsKey(postalCode)
    }
}
