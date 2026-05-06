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

