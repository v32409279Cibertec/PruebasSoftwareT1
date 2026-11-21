# PruebasSoftwareT1

Proyecto Maven con Java 21 para practicar pruebas unitarias con JUnit 5 y Mockito.

## Estructura

- Pregunta 1: `LoyaltyPointsCalculator` y `LoyaltyPointsCalculatorTest`.
- Pregunta 2: `PenaltyCalculator` y `PenaltyCalculatorTest`.
- Pregunta 3: `LoyaltyServiceImpl` y `LoyaltyServiceImplTest`.
- Pregunta 4: `SubscriptionServiceImpl` y `SubscriptionServiceImplTest`.

Las clases de negocio estan en `src/main/java` y las pruebas en `src/test/java`.

## Requisitos

- JDK 21.
- Maven 3.9.11 (se puede usar el Maven Wrapper incluido).
- Conexion a Internet para descargar dependencias la primera vez.

## Ejecutar todas las pruebas en Windows

```powershell
.\mvnw.cmd test
```

Tambien se pueden ejecutar desde IntelliJ IDEA haciendo clic derecho sobre `src/test/java` y seleccionando **Run All Tests**.

## Evidencias

Antes de entregar, ejecutar todas las pruebas y guardar capturas donde se vea el resultado verde, la cantidad de pruebas aprobadas y el nombre del proyecto.

## Nota sobre la pregunta 4

La interfaz `PaymentGatewayClient` contiene `isCardValid()`, que debe invocarse para conocer si la tarjeta esta vigente. Por eso, en el escenario de tarjeta vencida se verifica esa consulta y se comprueba que `chargeMonthlyFee()` nunca se ejecute. El flujo no puede tener cero llamadas totales a `PaymentGatewayClient` y, al mismo tiempo, consultar la validez de la tarjeta.
