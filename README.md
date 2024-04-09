# Descripción del POC

En el presente repositorio se incluye una POC que implementa un listado de paises. Por simplicidad se combina backend y frontend en un mismo módulo. 

Se utilizan las siguientes tecnologías y bibliotecas:

<b>Backend</b> 
- JAVA 8, Spring Boot, Mybatis, H2 (motor de base de datos, utilizado en este caso en formato "embedded")
- Gestión de dependencias utilizando Maven.

<b>Frontend</b>
- Thymeleaf, 
- HTML5, CSS y ES6 
- Bootstrap (4.6) y Datatables (https://datatables.net/)
- Gestión de dependencias mediante node/npm

## Requisitos de instalación

Para compilar y ejecutar el proyecto se requiere tener instalado:

- JAVA JDK 8
- Versión reciente de Maven (probado con la versión 3.6.3)
- Versión reciente de Node (probado con la versión 19.6.0)
- IDE de desarrollo de su preferencia.

## Instrucciones de compilación

El proyecto se compila ejecutando los siguientes comandos dentro de la ruta del proyecto:

1) <code>npm install</code> (sólo la primera vez)
2) <code>npm run build</code>
3) <code>mvn clean install -U -DskipTests</code>

>En algunos ambientes windows, la ejecución de <code>npm run build</code> puede fallar (pues no siempre la ejecución de programas javascript está asociado a node). Aquí se detalla un hilo que indica un workaround: https://github.com/javascriptair/site/issues/98

## Instrucciones de ejecución

De las múltiples formas de ejecutar el proyecto, resaltamos la siguiente: desde la línea de comandos ejecutar:

<code>mvn spring-boot:run</code>

## Acceso al POC

Apuntar navegador a http://localhost:8080/paises

### Nuevo en la PoC

> Apuntar navegador a http://localhost:8080/personas

# Enunciado prueba

1) Se debe descargar la POC e implementar sobre ella <b>nuevos endpoints en la API REST existente</b> que permita listar, registrar, editar y eliminar un nuevo recurso: <b>persona</b>. El recurso persona debe constar de los siguientes atributos:

- Nombres
- Apellidos
- Pais

>En concreto, debe crearse la tabla persona en el ddl de la POC: <code>/src/main/resources/schema.sql</code> junto con las construcciones necesarias para operar sobre ella bajo una arquitectura REST (<code>@RestController</code> y construcciones de acceso a BD)
 
2) Se debe implementar una <b>nueva pantalla</b> que despliegue el listado de personas con botones y/o acciones para eliminar, crear y editar una persona. Esta pantalla opera conectándose a los recursos REST de la API. La solicitud de los datos de creación y edición se debe realizar en una pantalla modal. La idea es nuevamente apoyarse en las tecnologías ya existentes en la POC. 

>En concreto, debe crearse un @Controller, una plantilla HTML y las clases .js necesarias para listar y mantener el concepto de persona

4) Se debe mantener la arquitectura existente, creando construcciones análogas (por ejemplo un <code>@RestController</code> de persona).

5) Se debe respetar el look & feel existente

6) Por favor compartir el resultado (código fuente) en el repositorio personal del postulante.

# Pauta de evaluación

Se medirá:

1) Funcionamiento de la aplicación
2) Calidad del código
3) Uso de la arquitectura ya definida






