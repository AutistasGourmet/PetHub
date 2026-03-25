package com.autistasgourmet.pethub.domain.model

enum class PetSpecies(val displayName: String) {
    PERRO("Perro"),
    GATO("Gato")
}

enum class PetAgeRange(val displayName: String) {
    CACHORRO("Cachorro"),
    JOVEN("Joven"),
    ADULTO("Adulto"),
    MAYOR("Mayor")
}

enum class PetSize(val displayName: String) {
    CHICO("Chico"),
    MEDIANO("Mediano"),
    GRANDE("Grande")
}

enum class PetGender(val displayName: String) {
    MACHO("Macho"),
    HEMBRA("Hembra")
}

enum class EnergyLevel(val displayName: String) {
    BAJO("Bajo"),
    MEDIO("Medio"),
    ALTO("Alto")
}

enum class SociabilityLevel(val displayName: String) {
    EXCELENTE("Excelente"),
    BUENA("Buena"),
    REGULAR("Regular"),
    MALA("Mala"),
    DESCONOCIDA("Desconocida")
}

enum class PetTrait(val displayName: String) {
    CARINOSO("Cariñoso"),
    SOCIABLE("Sociable"),
    TIMIDO("Tímido"),
    DESCONFIADO("Desconfiado"),
    JUGUETON("Juguetón"),
    ACTIVO("Activo"),
    PEREZOSO("Perezoso"),
    AGIL("Ágil"),
    TRANQUILO("Tranquilo"),
    INQUIETO("Inquieto"),
    CURIOSO("Curioso"),
    INTELIGENTE("Inteligente"),
    TRAVIESO("Travieso"),
    OBSERVADOR("Observador"),
    ANSIOSO("Ansioso"),
    ASTUTO("Astuto"),
    GLOTON("Glotón"),
    TERRITORIAL("Territorial"),
    PROTECTOR("Protector"),
    CAZADOR("Cazador"),
    LEAL("Leal")
}

enum class HousingType(val displayName: String) {
    APARTAMENTO("Apartamento"),
    CASA("Casa"),
    ENTORNO_RURAL("Entorno rural")
}

enum class AdopterExperience(val displayName: String) {
    PRIMERA_VEZ("Primera vez"),
    PASADO("He tenido perros y gatos en el pasado"),
    ACTUALMENTE("Tengo mascotas actualmente"),
    RESCATISTA("Tengo mucha experiencia / Soy rescatista")
}

enum class ZacatecasMunicipality(val displayName: String) {
    APOZOL("Apozol"),
    APULCO("Apulco"),
    ATOLINGA("Atolinga"),
    BENITO_JUAREZ("Benito Juárez"),
    CALERA("Calera"),
    CANITAS_DE_FELIPE_PESCADOR("Cañitas de Felipe Pescador"),
    CONCEPCION_DEL_ORO("Concepción del Oro"),
    CUAUHTEMOC("Cuauhtémoc"),
    CHALCHIHUITES("Chalchihuites"),
    FRESNILLO("Fresnillo"),
    TRINIDAD_GARCIA_DE_LA_CADENA("Trinidad García de la Cadena"),
    GENARO_CODINA("Genaro Codina"),
    GENERAL_ENRIQUE_ESTRADA("General Enrique Estrada"),
    GENERAL_FRANCISCO_R_MURGUIA("General Francisco R. Murguía"),
    EL_PLATEADO_DE_JOAQUIN_AMARO("El Plateado de Joaquín Amaro"),
    GENERAL_PANFILO_NATERA("General Pánfilo Natera"),
    GUADALUPE("Guadalupe"),
    HUANUSCO("Huanusco"),
    JALPA("Jalpa"),
    JEREZ("Jerez"),
    JIMENEZ_DEL_TEUL("Jiménez del Teul"),
    JUAN_ALDAMA("Juan Aldama"),
    JUCHIPILA("Juchipila"),
    LORETO("Loreto"),
    LUIS_MOYA("Luis Moya"),
    MAZAPIL("Mazapil"),
    MELCHOR_OCAMPO("Melchor Ocampo"),
    MEZQUITAL_DEL_ORO("Mezquital del Oro"),
    MIGUEL_AUZA("Miguel Auza"),
    MOMAX("Momax"),
    MONTE_ESCOBEDO("Monte Escobedo"),
    MORELOS("Morelos"),
    MOYAHUA_DE_ESTRADA("Moyahua de Estrada"),
    NOCHISTLAN_DE_MEJIA("Nochistlán de Mejía"),
    NORIA_DE_ANGELES("Noria de Ángeles"),
    OJOCALIENTE("Ojocaliente"),
    PANUCO("Pánuco"),
    PINOS("Pinos"),
    RIO_GRANDE("Río Grande"),
    SAIN_ALTO("Sain Alto"),
    EL_SALVADOR("El Salvador"),
    SOMBRERETE("Sombrerete"),
    SUSTICACAN("Susticacán"),
    TABASCO("Tabasco"),
    TEPECHITLAN("Tepechitlán"),
    TEPETONGO("Tepetongo"),
    TEUL_DE_GONZALEZ_ORTEGA("Teúl de González Ortega"),
    TLALTENANGO_DE_SANCHEZ_ROMAN("Tlaltenango de Sánchez Román"),
    VALPARAISO("Valparaíso"),
    VETAGRANDE("Vetagrande"),
    VILLA_DE_COS("Villa de Cos"),
    VILLA_GARCIA("Villa García"),
    VILLA_GONZALEZ_ORTEGA("Villa González Ortega"),
    VILLA_HIDALGO("Villa Hidalgo"),
    VILLANUEVA("Villanueva"),
    ZACATECAS("Zacatecas"),
    TRANCOSO("Trancoso"),
    SANTA_MARIA_DE_LA_PAZ("Santa María de la Paz")
}
