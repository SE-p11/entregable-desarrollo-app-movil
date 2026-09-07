# Sistema de Gestión de Inventario - App Móvil

Aplicación móvil desarrollada en Android Studio utilizando **Kotlin** y **Jetpack Compose** para la gestión e inventario de productos.

---

## ?? Características Principales

- **Autenticación de Usuarios**:
  - Registro e Inicio de sesión.
  - Control de sesiones persistentes (SessionManager).
- **Dashboard Principal**:
  - Navegación hacia los módulos principales de la aplicación.
- **Gestión de Inventario (CRUD)**:
  - Registro de nuevos productos con nombre, precio, cantidad e imagen.
  - Listado dinámico de productos registrados.
  - Edición de información de productos en tiempo real.
  - Eliminación de registros.
- **Reportes PDF**:
  - Generación de reportes tabulares de inventario exportables a formato PDF.

---

## ??? Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **Interfaz**: Jetpack Compose (Material Design 3)
- **Base de Datos Local**: SQLite (SQLiteOpenHelper)
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Generación de Documentos**: API PdfDocument nativa de Android

---

## ?? Estructura del Proyecto
