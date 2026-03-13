# PetHub 🐾 - Plataforma Comunitaria de Adopción y Rescate Animal

PetHub es una solución móvil diseñada para transformar la vida de los animales en situación de calle y facilitar procesos de adopción responsables. Basada en la colaboración comunitaria, la app integra tecnología de geolocalización y algoritmos de emparejamiento para conectar a rescatistas con adoptantes potenciales.

---

# Pilares del Proyecto

1. **Sistema de Matching:**  
   Algoritmo de compatibilidad entre el perfil del adoptante y las necesidades del animal.

2. **Mapa Interactivo:**  
   Reporte en tiempo real de animales extraviados o en situación de riesgo utilizando Google Maps SDK.

---

# Stack Tecnológico

- **Lenguaje:** Kotlin v.{por definirse}
- **UI Framework:** Jetpack Compose (Modern Android Toolkit)  
- **Backend:** Firebase (Authentication & Cloud Firestore)  
- **APIs:** Google Maps Platform
- **Arquitectura:** MVVM (Model-View-ViewModel)

---

# Configuración del Entorno (Setup)

Para ejecutar este proyecto localmente en tu entorno de desarrollo, sigue estos pasos:

## 1. Clonar el repositorio

```bash
git clone [url-del-repo]
```

## 2. Importar en Android Studio

Abre la carpeta raíz del proyecto en Android Studio.

## 3. Estructura del Equipo

Nuestro equipo opera bajo el Perfil Básico para Pequeñas Entidades del estándar ISO/IEC 29110, garantizando calidad y trazabilidad.

Integrante	Rol Oficial	Responsabilidad Principal
* Guillermo	Team Leader (TL)	Gestión de recursos, tiempos y visión del producto
* Patrick	Quality Leader (QL)	Aseguramiento de calidad, testing y validación de requisitos
* Sergio	Technical Leader (TechL)	Arquitectura de software, gestión de Firebase y seguridad
* Jesús	Collaboration Leader (CL)	Gestión del flujo de trabajo, repositorios y comunicación interna. Documentación Formal

Para consultar los artefactos formales de ingeniería de software, accede a nuestra carpeta compartida interna:

insertar_link[Especificación de Requisitos de Software (SRS)]

insertar_link[Matriz de Trazabilidad de Requisitos]

insertar_link[Documento de Diseño de Software (SDD)]

# Flujo Lógico de Trabajo (Git Workflow)

Para mantener la integridad del código y un Entorno de Desarrollo Controlado, utilizamos una estrategia estricta de Feature Branching.

La rama main refleja siempre un estado estable y de producción.

**Reglas del Repositorio:**

- La rama main está protegida
- Nunca se hace Push directo a main
- Se requiere abrir un Pull Request (*PR*) para cualquier integración
- Se requiere la aprobación de al menos un revisor (Code Review)
- Deben pasar las pruebas automáticas (GitHub Actions) antes del Merge

## **Pasos para integrar código:** 

| Paso | Acción           | Comando / Descripción                 |
| ---- | ---------------- | ------------------------------------- |
| 1️   | Sincronización   | `git pull origin main`                |
| 2️   | Creación de Rama | `git checkout -b feature/CU-01-login` |
| 3️   | Desarrollo       | Implementar funcionalidad             |
| 4️   | Push y PR        | Abrir Pull Request                    |
| 5️   | Validación       | Esperar CI + Code Review              |
| 6️   | Merge            | Squash and Merge                      |

# Convenciones de Nombramiento

Para garantizar la **rastreabilidad de los Casos de Uso (CU)** es obligatorio seguir estas reglas.

## 1. Formato obligatorio para nombrar ramas

| Elemento    | Formato / Ejemplo                  | Descripción                                         |
| ----------- | ---------------------------------- | --------------------------------------------------- |
| **Feature** | `feature/CU-01-login-firebase`     | Nueva funcionalidad del sistema                     |
| **Bugfix**  | `bugfix/CU-05-error-marcador-mapa` | Corrección de errores en funcionalidades existentes |
| **Chore**   | `chore/update-gradle-dependencies` | Tareas técnicas, mantenimiento o configuración      |
```bash
git switch -c <Elemento/ID-CU-descripcion-breve>
```
_Este comando crea una nueva rama y te cambia a ella_


---
## 2. Tipos de Commits

Cada commit debe ser **atómico** y, cuando aplique, incluir el **ID del Caso de Uso**.

| Tipo         | **Cuándo usarlo**                                     | **Ejemplo**                                                   |
| ------------ | ----------------------------------------------------- | ------------------------------------------------------------- |
| **feat**     | Nueva funcionalidad                                   | `feat(auth): agregar endpoint de registro en Firebase #CU-01` |
| **fix**      | Corrección de error                                   | `fix(login): corregir validación de contraseña vacía #CU-02`  |
| **docs**     | Cambios en documentación                              | `docs: actualizar instrucciones de despliegue`                |
| **style**    | Cambios de formato o estilo sin modificar lógica      | `style: aplicar formato con ktlint #CU-03`                    |
| **refactor** | Reestructuración de código sin cambiar comportamiento | `refactor(user): simplificar lógica del ViewModel #CU-04`     |
| **perf**     | Mejora de rendimiento                                 | `perf(query): optimizar consulta en Firestore #CU-05`         |
| **test**     | Agregar o modificar pruebas                           | `test(auth): añadir casos para registro #CU-01`               |
| **build**    | Cambios en dependencias o sistema de compilación      | `build(gradle): actualizar versión de Jetpack Compose`        |
| **ci**       | Cambios en integración continua                       | `ci: ajustar pipeline para ejecutar tests en PRs`             |
| **chore**    | Tareas menores de mantenimiento                       | `chore: actualizar dependencias menores`                      |
| **revert**   | Revertir un commit previo                             | `revert: revertir feat(auth)`                                 |

## 3. Ejemplos de Ramas y Commits

| Caso de Uso         | **Rama**                       | **Commit**                                          |
| ------------------- | ------------------------------ | --------------------------------------------------- |
| **Login**           | `feature/CU-01-login-firebase` | `feat(auth): implementar login con Firebase #CU-01` |
| **Reporte en mapa** | `feature/CU-03-map-reports`    | `feat(map): agregar marcador de reporte #CU-03`     |
| **Error marcador**  | `bugfix/CU-03-marker-error`    | `fix(map): corregir error de renderizado #CU-03`    |
