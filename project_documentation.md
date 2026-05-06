# Documentación del Proyecto: PetHub

PetHub es una aplicación de adopción de mascotas diseñada con una arquitectura moderna, escalable y mantenible. Utiliza **MVVM (Model-View-ViewModel)** junto con **Clean Architecture** para separar las responsabilidades de manera clara.

## 🏗️ Arquitectura General

El proyecto sigue los principios de **Clean Architecture**, dividiendo la lógica en tres capas principales:

1.  **Capa de UI (Presentación)**: Pantallas de Jetpack Compose y ViewModels.
2.  **Capa de Domain (Dominio)**: Lógica de negocio pura (UseCases, Modelos de dominio e interfaces de Repositorios).
3.  **Capa de Data (Datos)**: Implementaciones de repositorios, acceso a Firebase (Firestore, Auth, Storage) y mapeo de datos.

### Flujo de Datos
`UI (Compose) ↔ ViewModel ↔ UseCase ↔ Repository ↔ Data Source (Firebase)`

---

## 📁 Estructura de Carpetas

La raíz del código fuente se encuentra en `app/src/main/java/com/autistasgourmet/pethub/`.

### 1. `data/` (Capa de Datos)
Contiene la implementación de cómo se obtienen y guardan los datos.
-   **`remote/dto/`**: Objetos de Transferencia de Datos (DTO) que representan la estructura en Firestore.
    -   `PetDto.kt`, `UserDto.kt`, `AdopterProfileDto.kt`.
-   **`repository/`**: Implementaciones concretas de las interfaces definidas en el dominio.
    -   `PetRepositoryImpl.kt`: Gestiona operaciones de mascotas en Firestore.
    -   `AuthRepositoryImpl.kt`: Gestiona autenticación con Firebase Auth.
    -   `StorageRepositoryImpl.kt`: (Si aplica) Gestiona archivos en Firebase Storage.
-   **`mapper/`**: Funciones de extensión para convertir entre DTOs (datos crudos) y Modelos de Dominio (datos de negocio).
    -   `Mappers.kt`.

### 2. `domain/` (Capa de Dominio)
Es el "corazón" de la aplicación. No depende de ninguna librería externa (excepto Kotlin/Coroutines).
-   **`model/`**: Entidades de negocio que usa toda la app.
    -   `Pet.kt`, `User.kt`, `AdopterProfile.kt`.
-   **`repository/`**: Interfaces (contratos) que definen qué operaciones se pueden hacer, pero no cómo.
-   **`usecase/`**: Clases que contienen una única acción de negocio. Ayudan a que los ViewModels no tengan lógica compleja.
    -   `LoginUseCase.kt`: Valida credenciales y gestiona el inicio de sesión con Firebase Auth.
    -   `RegisterUseCase.kt`: Gestiona la creación de nuevas cuentas de usuario.
    -   `SavePetUseCase.kt`: Registra o actualiza mascotas, integrando lógica de ubicación mediante municipios de Zacatecas.
    -   `CompleteAdopterProfileUseCase.kt`: Guarda el perfil del adoptante y normaliza su ubicación.
    -   `GetAdoptablePetsUseCase.kt`: Recupera mascotas disponibles filtrando las que pertenecen al usuario actual.
    -   `ExpressInterestUseCase.kt`: Registra el interés de un usuario por una mascota (Like).
    -   `GetPetDetailUseCase.kt`: Obtiene la información completa de una mascota específica.
    -   `GetMatchesUseCase.kt`: Lógica de cruce de datos para mostrar candidatos interesados a los dueños.

### 3. `ui/` (Capa de Presentación)
Todo lo relacionado con la interfaz de usuario.
-   **`features/`**: Organizado por funcionalidades. Cada una suele tener su `Screen.kt` y `ViewModel.kt`.
    -   `adopt/`: Pantalla de exploración de mascotas.
    -   `home/`: Pantalla principal/dashboard.
    -   `login/` / `register/`: Flujo de autenticación.
    -   `matches/`: Sistema de intereses y coincidencias.
    -   `profile/`: Gestión del perfil del adoptante.
    -   `registerPet/`: Formulario para dar de alta una mascota.
-   **`components/`**: Widgets reutilizables (Botones, Cards, TextFields personalizados).
    -   `appButtons/`, `appCards/`, `appFields/`, `media/`, etc.
-   **`navigation/`**: Configuración de rutas y grafos de navegación (Jetpack Navigation).
    -   `NavGraph.kt`, `Routes.kt`.
-   **`theme/`**: Configuración de colores, tipografía y tema visual (Material 3).

### 4. `di/` (Inyección de Dependencias)
-   **`AppModule.kt`**: Configuración de **Hilt**. Aquí se definen cómo se crean las instancias de Firebase y cómo se inyectan los repositorios y casos de uso.

---

## 🔑 Funcionalidades Clave

1.  **Autenticación**: Registro e inicio de sesión gestionado por Firebase Auth.
2.  **Gestión de Mascotas**: Alta de mascotas con soporte para múltiples fotos y selección de ubicación por municipio (Zacatecas).
3.  **Sistema de Adopción (Matching)**:
    -   Los usuarios pueden expresar interés en mascotas.
    -   El sistema prioriza mascotas en el mismo municipio que el adoptante.
    -   Los dueños pueden ver quién está interesado en sus mascotas.
4.  **Perfiles de Adoptante**: Formulario detallado para que los adoptantes completen su información, mejorando el proceso de selección.
5.  **Caché Local**: Gracias a las capacidades nativas de Firebase Firestore, la aplicación funciona sin conexión, cacheando los datos consultados previamente.

---

## 🛠️ Tecnologías Utilizadas

-   **Lenguaje**: Kotlin
-   **UI**: Jetpack Compose (Declarativo)
-   **Inyección de Dependencias**: Hilt
-   **Base de Datos**: Firebase Firestore (con persistencia local)
-   **Autenticación**: Firebase Auth
-   **Asincronía**: Coroutines & Flow
-   **Arquitectura**: Clean Architecture + MVVM

---

## 📝 Resumen de Archivos Importantes

-   **`MainActivity.kt`**: Punto de entrada, configura el host de navegación.
-   **`PetHubApp.kt`**: Clase Application donde se inicializa Hilt.
-   **`AppModule.kt`**: El "cerebro" de la inyección de dependencias.
-   **`Mappers.kt`**: Crucial para mantener la separación de capas (Data vs Domain).
-   **`Routes.kt`**: Definición centralizada de todas las pantallas de la app.
