<!-- evaluacion:inicio -->
# Evaluación — Mackinnon-Masi/Fisica_Cool

> Documento **generado automáticamente** por la cátedra de Programación II (RepoSense + análisis de calidad POO/SOLID). **No editar a mano**: se regenera en cada evaluación.
> Rama evaluada: `main`. Análisis **heurístico** (regex + métricas): es una criba para orientar la revisión, **no una nota**.
> Generado: 2026-11-02.

## 1. Veredicto

| Aspecto | Resultado |
|---|---|
| Índice de calidad POO/SOLID | **7/100** |
| Código (LOC) | 1,496 |
| Archivos de código | 23 |
| Clases (bien usadas) | 26 (3 OK) |
| Código dentro de clases (%POO) | 96% |
| Funciones fuera de clases | 1 |
| Duplicación lógica | 0.0% |
| Señales S/O/L/I/D | 6/0/2/0/6 |

> Este informe **describe** lo que el análisis midió con los criterios ya establecidos de la materia. No es una nota.

### Semáforo de aprobación

Criterios de `CRITERIOS-CALIDAD.md`. **Aprobar** se juega con el perfil `estandar`; **destacar**, con el `exigente` (meta de excelencia).

| Semáforo | Resultado |
|---|---|
| Aprobar (`estandar`) | **no cumple** — 3/4 |
| Destacar (`exigente`) | **no cumple** — 3/5 |

**Criterios para aprobar**

| Criterio | Requisito | Tu resultado | Cumple |
|---|---|---|---|
| Código dentro de clases (%POO) | ≥ 85% | 96% | sí |
| Duplicación lógica | ≤ 10% | 0.0% | sí |
| Funciones fuera de clases | ≤ 1 | 1 | sí |
| Señales POO/SOLID críticas | ninguna | Acoplamiento alto (CBO), Override vacío / 'no implementado' (LSP) | no |

**Criterios para destacar**

| Criterio | Requisito | Tu resultado | Cumple |
|---|---|---|---|
| Código dentro de clases (%POO) | ≥ 95% | 96% | sí |
| Duplicación lógica | ≤ 5% | 0.0% | sí |
| Funciones fuera de clases | ≤ 1 | 1 | sí |
| Señales POO/SOLID críticas | ninguna | Clase demasiado grande (SRP), Baja cohesión (LCOM), Complejidad alta, Acoplamiento alto (CBO), Override vacío / 'no implementado' (LSP) | no |
| Índice de calidad POO/SOLID | ≥ 60 | 7/100 | no |

### Cómo se calcula el %POO (y por qué no sube solo)

`%POO = líneas de lógica dentro de clases ÷ líneas de lógica totales`.

Se cuentan las líneas de **lógica y campos**; no cuentan `package`/`import`/`using`/anotaciones ni comentarios, Javadoc o docstrings. Es una **proporción**: si agregás código nuevo dentro de una clase, el numerador y el denominador crecen juntos y el porcentaje casi no cambia.

Sube cuando el código que hoy está **fuera** de clases pasa a ser método de una clase (o desaparece): funciones sueltas, `main`, constantes de módulo, scripts y lógica de archivos sueltos. También importa que ese trabajo esté **integrado en `main`**: la evaluación siempre mide `main`, así que lo que sigue en otra rama no se refleja todavía.

## 2. Autoría (RepoSense)

| Autor | Commits | Código (LOC) | Tests (LOC) | Docs (LOC) | Días activos | % del código |
|---|---:|---:|---:|---:|---:|---:|
| LauMackinnon | 19 | 1,065 | 52 | 1 | 13 | 65% |
| mfmasi | 14 | 573 | 0 | 11 | 10 | 35% |

> _El LOC de autoría lo calcula RepoSense sobre el historial y puede diferir del LOC de código del análisis de calidad (que mira solo el estado de `main`)._

> **Reparto del trabajo:** LauMackinnon concentra el 65% de las líneas de código. Un reparto muy desigual suele indicar que la integración no fue realmente en equipo; vale la pena reconstruir cómo se coordinó.

## 3. Fallas detectadas

Reúne lo que el análisis marcó y lo que todavía falta para cada semáforo. Cuando la señal se puede asociar a archivos, se indican para orientar la revisión. No es una nota.

| Falla | Tu resultado | Requisito | Archivos |
|---|---|---|---|
| Señales POO/SOLID críticas | Acoplamiento alto (CBO), Override vacío / 'no implementado' (LSP) | Aprobar: ninguna<br>Destacar: ninguna | `app/src/main/kotlin/org/example/fisicas/CalculadoraTrayectoria.kt`, `app/src/main/kotlin/org/example/fisicas/SimulacionTrayectoria.kt`, `app/src/main/kotlin/org/example/visuales/ControladorTrayectoria.kt`, `app/src/main/kotlin/org/example/visuales/Creditos.kt`, `app/src/main/kotlin/org/example/visuales/PanelAyudaControles.kt`, `app/src/main/kotlin/org/example/visuales/PanelMetricasTrayectoria.kt` (+4 más) |
| Índice de calidad POO/SOLID | 7/100 | Destacar: ≥ 60 | — |
| Métodos/funciones largos o con muchos parámetros | **3** método(s)/función(es) largos o con muchos parámetros | Sin señales | `app/src/main/kotlin/org/example/fisicas/CalculadoraTrayectoria.kt`, `app/src/main/kotlin/org/example/trackeo/KeyboardReader.kt`, `app/src/main/kotlin/org/example/visuales/Pantalla.kt` |
| Dependencia de concretos y falta de abstracciones (DIP) | **6** dependencia(s) de concretos sin abstracciones | Sin señales | `app/src/main/kotlin/org/example/visuales/Creditos.kt`, `app/src/main/kotlin/org/example/visuales/PanelAyudaControles.kt`, `app/src/main/kotlin/org/example/visuales/PanelMetricasTrayectoria.kt`, `app/src/main/kotlin/org/example/visuales/Pantalla.kt`, `app/src/main/kotlin/org/example/visuales/VistaSimulacion.kt`, `app/src/test/kotlin/org/example/AppTest.kt` |
| Clase sin métodos (solo datos) | **4** clase(s) sin métodos (solo datos) | Sin señales | `app/src/main/kotlin/org/example/fisicas/CalculadoraTrayectoria.kt`, `app/src/main/kotlin/org/example/fisicas/SimulacionTrayectoria.kt`, `app/src/main/kotlin/org/example/trackeo/KeyboardReader.kt` |
| Campos públicos (encapsulación) | **11** clase(s) con campos públicos | Sin señales | `app/src/main/kotlin/org/example/fisicas/CalculadoraTrayectoria.kt`, `app/src/main/kotlin/org/example/fisicas/Proyectil.kt`, `app/src/main/kotlin/org/example/fisicas/SimulacionTrayectoria.kt`, `app/src/main/kotlin/org/example/visuales/Inicio.kt`, `app/src/main/kotlin/org/example/visuales/PanelAyudaControles.kt`, `app/src/main/kotlin/org/example/visuales/PanelMetricasTrayectoria.kt` (+3 más) |

## 4. Aspectos evaluados

Cada aspecto indica **qué mide**, **por qué importa** y **tu resultado**. Cuando el análisis reportó una señal, se agregan preguntas para **investigar la causa** (no hay recetas: entender el porqué es parte de la evaluación).

### 1. Índice de calidad POO/SOLID (indice)

- **Qué mide:** Un resumen numérico (0-100) que combina la riqueza de clases bien usadas con la penalización por señales POO/SOLID y duplicación, y luego aplica topes duros.
- **Por qué importa:** Ordena y prioriza la revisión: un índice bajo indica varias señales que conviene mirar juntas.
- **Tu resultado:** índice **7/100**

### 2. Duplicación lógica (DRY) (duplicacion) — sin señales

- **Qué mide:** Porcentaje de bloques de LÓGICA repetidos. Quedan fuera imports/includes, anotaciones, declaraciones de campos y comentarios/Javadoc/docstrings: esos bloques son boilerplate, no lógica.
- **Por qué importa:** Cada copia de lógica se mantiene por separado: un arreglo hay que repetirlo y los defectos se multiplican.
- **Tu resultado:** **0.0%** de duplicación lógica

### 3. Adopción de POO (% de código en clases) (poo) — sin señales

- **Qué mide:** Proporción de las **líneas de lógica** que viven dentro de una clase. Quedan fuera `package`/`import`/`using`/anotaciones y comentarios/Javadoc/docstrings: no son código que pueda estar dentro de una clase y, si contaran, el porcentaje quedaría topado por el boilerplate de cada archivo.
- **Por qué importa:** El diseño orientado a objetos es el eje de la materia: el comportamiento debería vivir como método de una clase.
- **Tu resultado:** **95.8%** del código dentro de clases
- **Cómo leerlo:** Es una **proporción**, no un acumulado: agregar código dentro de una clase no la sube por sí solo (numerador y denominador crecen juntos). Sube cuando el código que está **fuera** de clases (funciones sueltas, `main`, constantes de módulo, scripts) pasa a ser método de una clase o deja de existir. La medición se hace sobre `main`: el trabajo que todavía vive en otra rama no se refleja hasta que se integra.

### 4. Funciones fuera de clases (sueltas) — sin señales

- **Qué mide:** Funciones de nivel de módulo que no pertenecen a ninguna clase (en esta materia el comportamiento debe ser método de una clase).
- **Por qué importa:** Son la forma más directa de código procedural en un proyecto que debería ser orientado a objetos.
- **Tu resultado:** **1** función(es) fuera de clases

### 5. Clase demasiado grande (SRP) (god) — con señales

- **Qué mide:** Clases con demasiadas líneas o métodos de LÓGICA. No cuentan los getters/setters triviales ni la cantidad de campos por sí sola.
- **Por qué importa:** Una clase que hace de todo cambia por muchos motivos y cuesta entenderla y probarla.
- **Tu resultado:** **1** clase(s) demasiado grande(s)
- **Para investigar la causa:**
    - ¿La clase creció por acumulación de responsabilidades?
    - ¿Qué partes de la clase se usan siempre juntas y cuáles no?
    - ¿Se podría describir su responsabilidad en una sola frase?

### 6. Baja cohesión (LCOM) (cohesion) — con señales

- **Qué mide:** Métodos y campos de una clase que casi no se relacionan entre sí.
- **Por qué importa:** Una unidad que cambia por motivos distintos y comparte poca información suele esconder más de una responsabilidad.
- **Tu resultado:** **1** clase(s) con baja cohesión
- **Para investigar la causa:**
    - ¿Qué métodos de la clase no comparten estado con el resto?
    - ¿Cambian por motivos distintos según la tarea?
    - ¿Se pensó su responsabilidad antes de escribirla, o se fue agregando?

### 7. Complejidad alta (complejidad) — con señales

- **Qué mide:** Complejidad ciclomática: cantidad de caminos posibles dentro de un método o función (condicionales, bucles, casos).
- **Por qué importa:** Muchos caminos = difícil de seguir y de probar; suelen esconderse casos sin cubrir.
- **Tu resultado:** **1** método(s)/función(es) con complejidad alta
- **Para investigar la causa:**
    - ¿Se agregaron condiciones a medida que aparecían casos nuevos?
    - ¿Se probaron todos los caminos o solo el caso principal?
    - ¿Hay reglas de negocio mezcladas con detalles técnicos?

### 8. Métodos/funciones largos o con muchos parámetros (tamano) — con señales

- **Qué mide:** Tamaño de los métodos/funciones y cantidad de parámetros.
- **Por qué importa:** Una función que hace varias cosas cuesta leerla, nombrarla y reutilizarla.
- **Tu resultado:** **3** método(s)/función(es) largos o con muchos parámetros
- **Para investigar la causa:**
    - ¿La función creció por acumulación de pasos?
    - ¿Agrupa operaciones que podrían describirse por separado?
    - ¿Los parámetros representan una misma idea (un objeto) o cosas sueltas?

### 9. Acoplamiento alto (CBO) (acoplamiento) — con señales

- **Qué mide:** Cuánto depende una clase de otros tipos del proyecto.
- **Por qué importa:** Piezas muy enlazadas hacen que un cambio se propague y que sea difícil reemplazar partes.
- **Tu resultado:** **8** clase(s) muy acoplada(s)
- **Para investigar la causa:**
    - ¿La clase conoce detalles internos de muchas otras?
    - ¿Se puede cambiar una parte sin tocar las demás?
    - ¿Los colaboradores llegan como parámetros o se crean por dentro?

### 10. Herencia profunda (DIT) (herencia) — sin señales

- **Qué mide:** Profundidad de la cadena de herencia de una clase.
- **Por qué importa:** Jerarquías muy profundas vuelven difícil saber de dónde viene cada comportamiento.
- **Tu resultado:** **0** clase(s) con herencia profunda

### 11. Switch sobre tipos (OCP) (ocp) — sin señales

- **Qué mide:** Chequeos repetidos de tipo (`instanceof`/`is`/`typeof`) que equivalen a un switch sobre el tipo.
- **Por qué importa:** Cada tipo nuevo obliga a tocar varios lugares, en vez de extender con una implementación nueva.
- **Tu resultado:** **0** clase(s) con switch sobre tipos

### 12. Interfaz con demasiados métodos (ISP) (isp) — sin señales

- **Qué mide:** Interfaces o clases abstractas con muchos métodos declarados.
- **Por qué importa:** Obliga a implementar operaciones que no se usan y acopla a los clientes a métodos que no necesitan.
- **Tu resultado:** **0** interfaz(es) con demasiados métodos

### 13. Override vacío o 'no implementado' (LSP) (lsp) — con señales

- **Qué mide:** Métodos sobrescritos que quedan vacíos o lanzan 'no implementado'.
- **Por qué importa:** Suele indicar que la jerarquía no representa bien el problema y rompe la sustitución entre clases.
- **Tu resultado:** **2** override(s) vacío(s) o 'no implementado'
- **Para investigar la causa:**
    - ¿Por qué ese método no tiene comportamiento en esa clase?
    - ¿La clase hereda algo que en realidad no le corresponde?
    - ¿Qué contrato se esperaba cumplir al heredar?

### 14. Dependencia de concretos y falta de abstracciones (DIP) (dip) — con señales

- **Qué mide:** Instanciación directa de clases concretas y ausencia de interfaces o clases abstractas pese al acoplamiento.
- **Por qué importa:** Depender de implementaciones concretas dificulta cambiar o probar una parte sin arrastrar las demás.
- **Tu resultado:** **6** dependencia(s) de concretos sin abstracciones
- **Para investigar la causa:**
    - ¿La clase crea por dentro a sus colaboradores en vez de recibirlos?
    - ¿Hay puntos de variación que convendría expresar como abstracción?
    - ¿Cómo se probaría esa clase cambiando una implementación?

### 15. Clase sin métodos (solo datos) (data) — con señales

- **Qué mide:** Clases que solo acumulan campos y no tienen comportamiento.
- **Por qué importa:** Separar datos de comportamiento reparte por el proyecto la lógica que debería estar cerca de esos datos.
- **Tu resultado:** **4** clase(s) sin métodos (solo datos)
- **Para investigar la causa:**
    - ¿Dónde vive el comportamiento que opera sobre esos datos?
    - ¿La clase representa un concepto del dominio o un contenedor técnico?
    - ¿Se evita la lógica de negocio dentro de la clase por comodidad?

### 16. Campos públicos (encapsulación) (encapsulacion) — con señales

- **Qué mide:** Clases que exponen campos sin control de acceso.
- **Por qué importa:** Expone el estado interno y hace que cualquier cambio dependa de quién lo modifica desde afuera.
- **Tu resultado:** **11** clase(s) con campos públicos
- **Para investigar la causa:**
    - ¿Se accede al campo desde otras clases para evitar un método?
    - ¿Qué reglas deberían respetarse al modificar ese estado?
    - ¿Los campos públicos se usan más como configuración que como estado?

### 17. Clase solo con métodos estáticos (utilitaria) (static) — sin señales

- **Qué mide:** Clases donde todos los métodos son estáticos.
- **Por qué importa:** Es comportamiento procedural disfrazado de clase: no se instancia ni participa del modelo de objetos.
- **Tu resultado:** **0** clase(s) solo con métodos estáticos

## 5. Evolución por commit

Métricas calculadas sobre el estado de `main` en **cada commit** (22 commits con trabajo), del primero al último. La calidad usa el perfil `exigente`; el aporte de código lo mide RepoSense. El índice puede **bajar** al crecer el proyecto: se acumulan señales.

```mermaid
xychart-beta
    title "Índice POO/SOLID a lo largo de los commits"
    x-axis ["f7645c2", "62b1a6d", "b1e91df", "0211b91", "56a8f79", "fa3b047", "191ea3c", "e86fc1a", "580c966", "3cc7274", "78ab355", "b488da6", "61b98c8", "30f9976", "77e899d", "61fb4df", "9633dd2", "72b08f9", "fd73c60", "4249243", "bbdf447", "f2ac041"]
    y-axis "Índice" 0 --> 100
    line [0, 0, 0, 38, 21, 21, 15, 14, 12, 10, 10, 9, 8, 10, 10, 10, 10, 10, 10, 9, 9, 7]
```

| # | Fecha | Commit | Autor | LOC | Índice | %POO | Duplic. | Sueltas |
|---|---|---|---|---:|---:|---:|---:|---:|
| 1 | 2026-08-14 | `f7645c2` | LauMackinnon | 0 | 0 | 0% | 0.0% | 0 |
| 2 | 2026-08-14 | `62b1a6d` | mfmasi | 0 | 0 | 0% | 0.0% | 0 |
| 3 | 2026-08-14 | `b1e91df` | mfmasi | 0 | 0 | 0% | 0.0% | 0 |
| 4 | 2026-08-14 | `0211b91` | LauMackinnon | 76 | 38 | 26% | 0.0% | 1 |
| 5 | 2026-08-24 | `56a8f79` | dqmdz-etec | 107 | 21 | 34% | 0.0% | 1 |
| 6 | 2026-08-24 | `fa3b047` | dqmdz-etec | 107 | 21 | 34% | 0.0% | 1 |
| 7 | 2026-08-28 | `191ea3c` | mfmasi | 204 | 15 | 69% | 0.0% | 1 |
| 8 | 2026-09-07 | `e86fc1a` | LauMackinnon | 314 | 14 | 77% | 0.0% | 1 |
| 9 | 2026-09-28 | `580c966` | mfmasi | 417 | 12 | 84% | 0.0% | 1 |
| 10 | 2026-10-09 | `3cc7274` | LauMackinnon | 482 | 10 | 87% | 6.2% | 1 |
| 11 | 2026-10-09 | `78ab355` | mfmasi | 481 | 10 | 87% | 6.2% | 1 |
| 12 | 2026-10-09 | `b488da6` | LauMackinnon | 690 | 9 | 92% | 3.9% | 1 |
| 13 | 2026-10-16 | `61b98c8` | mfmasi | 1,032 | 8 | 95% | 2.4% | 1 |
| 14 | 2026-10-23 | `30f9976` | LauMackinnon | 1,159 | 10 | 96% | 0.0% | 1 |
| 15 | 2026-10-23 | `77e899d` | mfmasi | 1,195 | 10 | 96% | 0.0% | 1 |
| 16 | 2026-10-23 | `61fb4df` | mfmasi | 1,272 | 10 | 96% | 0.0% | 1 |
| 17 | 2026-10-23 | `9633dd2` | mfmasi | 1,285 | 10 | 96% | 0.0% | 1 |
| 18 | 2026-10-26 | `72b08f9` | mfmasi | 1,315 | 10 | 96% | 0.0% | 1 |
| 19 | 2026-10-30 | `fd73c60` | LauMackinnon | 1,318 | 10 | 95% | 0.0% | 1 |
| 20 | 2026-10-30 | `4249243` | mfmasi | 1,417 | 9 | 96% | 0.0% | 1 |
| 21 | 2026-10-30 | `bbdf447` | mfmasi | 1,417 | 9 | 96% | 0.0% | 1 |
| 22 | 2026-11-02 | `f2ac041` | mfmasi | 1,496 | 7 | 96% | 0.0% | 1 |

---
_Informe generado por la cátedra. Los criterios y umbrales de medición corresponden al perfil `exigente` de `calidad.py`; el semáforo de aprobación, a `CRITERIOS-CALIDAD.md` (ver `README.md`). Ante dudas, revisá el archivo señalado._
<!-- evaluacion:fin -->
