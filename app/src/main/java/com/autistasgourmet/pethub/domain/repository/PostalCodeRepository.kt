package com.autistasgourmet.pethub.domain.repository

import com.autistasgourmet.pethub.domain.model.ZacatecasMunicipality

interface PostalCodeRepository {
    /**
     * Obtiene el municipio de zacatecas con el codigo postal,
     * si no exite un municipio en zacatecas con ese codigo postal regresa null
     */
    fun getMunicipalityForPostalCode(postalCode: String): ZacatecasMunicipality?

    /**
     * Obtiene la lista de codigos postales por municipio
     */
    fun getPostalCodesForMunicipality(municipality: ZacatecasMunicipality): List<String>

    /**
     * Verifica si el codigo postal pertenece a zacatecas, osea a algun municipio del estado
     */
    fun isValidPostalCode(postalCode: String): Boolean
}
