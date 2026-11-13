<!-- evaluacion:inicio -->
# Evaluación — Mackinnon-Masi/Fisica_Cool

> Documento **generado automáticamente** por la cátedra de Programación II (RepoSense + análisis de calidad POO/SOLID). **No editar a mano**: se regenera en cada evaluación.
> Rama evaluada: `main`. Análisis **heurístico** (regex + métricas): es una criba para orientar la revisión, **no una nota**.
> Generado: 2026-11-13.

## 1. Veredicto

| Aspecto | Resultado |
|---|---|
| Índice de calidad POO/SOLID | **56/100** |
| Código (LOC) | 1,417 |
| Archivos de código | 21 |
| Clases (bien usadas) | 20 (5 OK) |
| Código dentro de clases (%POO) | 96% |
| Funciones fuera de clases | 0 |
| Duplicación lógica | 0.0% |
| Señales S/O/L/I/D | 1/0/0/0/4 |

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
| Funciones fuera de clases | ≤ 1 | 0 | sí |
| Señales POO/SOLID críticas | ninguna | Baja cohesión (LCOM) | no |

**Criterios para destacar**

| Criterio | Requisito | Tu resultado | Cumple |
|---|---|---|---|
| Código dentro de clases (%POO) | ≥ 95% | 96% | sí |
| Duplicación lógica | ≤ 5% | 0.0% | sí |
| Funciones fuera de clases | ≤ 1 | 0 | sí |
| Señales POO/SOLID críticas | ninguna | Baja cohesión (LCOM) | no |
| Índice de calidad POO/SOLID | ≥ 60 | 56/100 | no |

### Cómo se calcula el %POO (y por qué no sube solo)

`%POO = líneas de lógica dentro de clases ÷ líneas de lógica totales`.

Se cuentan las líneas de **lógica y campos**; no cuentan `package`/`import`/`using`/anotaciones ni comentarios, Javadoc o docstrings. Es una **proporción**: si agregás código nuevo dentro de una clase, el numerador y el denominador crecen juntos y el porcentaje casi no cambia.

Sube cuando el código que hoy está **fuera** de clases pasa a ser método de una clase (o desaparece): funciones sueltas, `main`, constantes de módulo, scripts y lógica de archivos sueltos. También importa que ese trabajo esté **integrado en `main`**: la evaluación siempre mide `main`, así que lo que sigue en otra rama no se refleja todavía.

## 2. Autoría (RepoSense)

| Autor | Commits | Código (LOC) | Tests (LOC) | Docs (LOC) | Días activos | % del código |
|---|---:|---:|---:|---:|---:|---:|
| LauMackinnon | 19 | 1,104 | 52 | 1 | 13 | 72% |
| mfmasi | 13 | 438 | 0 | 11 | 9 | 28% |

> _El LOC de autoría lo calcula RepoSense sobre el historial y puede diferir del LOC de código del análisis de calidad (que mira solo el estado de `main`)._

> **Reparto del trabajo:** LauMackinnon concentra el 72% de las líneas de código. Un reparto muy desigual suele indicar que la integración no fue realmente en equipo; vale la pena reconstruir cómo se coordinó.

## 3. Fallas detectadas

Reúne lo que el análisis marcó y lo que todavía falta para cada semáforo. Cuando la señal se puede asociar a archivos, se indican para orientar la revisión. No es una nota.

| Falla | Tu resultado | Requisito | Archivos |
|---|---|---|---|
| Señales POO/SOLID críticas | Baja cohesión (LCOM) | Aprobar: ninguna<br>Destacar: ninguna | `app/src/main/kotlin/org/example/visuales/VistaSimulacion.kt` |
| Índice de calidad POO/SOLID | 56/100 | Destacar: ≥ 60 | — |
| Métodos/funciones largos o con muchos parámetros | **3** método(s)/función(es) largos o con muchos parámetros | Sin señales | `app/src/main/kotlin/org/example/visuales/Pantalla.kt`, `app/src/main/kotlin/org/example/visuales/trayectoria/VisualizadorRecorrido.kt`, `app/src/main/kotlin/org/example/visuales/trayectoria/VisualizadorTrayectoria.kt` |
| Dependencia de concretos y falta de abstracciones (DIP) | **4** dependencia(s) de concretos sin abstracciones | Sin señales | `app/src/main/kotlin/org/example/visuales/Pantalla.kt`, `app/src/main/kotlin/org/example/visuales/VistaSimulacion.kt`, `app/src/main/kotlin/org/example/visuales/trayectoria/VisualizadorTrayectoria.kt`, `app/src/test/kotlin/org/example/AppTest.kt` |
| Campos públicos (encapsulación) | **7** clase(s) con campos públicos | Sin señales | `app/src/main/kotlin/org/example/fisicas/CalculadoraTrayectoria.kt`, `app/src/main/kotlin/org/example/fisicas/Proyectil.kt`, `app/src/main/kotlin/org/example/fisicas/SimulacionTrayectoria.kt`, `app/src/main/kotlin/org/example/visuales/Inicio.kt`, `app/src/main/kotlin/org/example/visuales/VistaSimulacion.kt`, `app/src/main/kotlin/org/example/visuales/trayectoria/VisualizadorAngulo.kt` (+1 más) |

## 4. Aspectos evaluados

Cada aspecto indica **qué mide**, **por qué importa** y **tu resultado**. Cuando el análisis reportó una señal, se agregan preguntas para **investigar la causa** (no hay recetas: entender el porqué es parte de la evaluación).

### 1. Índice de calidad POO/SOLID (indice)

- **Qué mide:** Un resumen numérico (0-100) que combina la riqueza de clases bien usadas con la penalización por señales POO/SOLID y duplicación, y luego aplica topes duros.
- **Por qué importa:** Ordena y prioriza la revisión: un índice bajo indica varias señales que conviene mirar juntas.
- **Tu resultado:** índice **56/100**

### 2. Duplicación lógica (DRY) (duplicacion) — sin señales

- **Qué mide:** Porcentaje de bloques de LÓGICA repetidos. Quedan fuera imports/includes, anotaciones, declaraciones de campos y comentarios/Javadoc/docstrings: esos bloques son boilerplate, no lógica.
- **Por qué importa:** Cada copia de lógica se mantiene por separado: un arreglo hay que repetirlo y los defectos se multiplican.
- **Tu resultado:** **0.0%** de duplicación lógica

### 3. Adopción de POO (% de código en clases) (poo) — sin señales

- **Qué mide:** Proporción de las **líneas de lógica** que viven dentro de una clase. Quedan fuera `package`/`import`/`using`/anotaciones y comentarios/Javadoc/docstrings: no son código que pueda estar dentro de una clase y, si contaran, el porcentaje quedaría topado por el boilerplate de cada archivo.
- **Por qué importa:** El diseño orientado a objetos es el eje de la materia: el comportamiento debería vivir como método de una clase.
- **Tu resultado:** **95.5%** del código dentro de clases
- **Cómo leerlo:** Es una **proporción**, no un acumulado: agregar código dentro de una clase no la sube por sí solo (numerador y denominador crecen juntos). Sube cuando el código que está **fuera** de clases (funciones sueltas, `main`, constantes de módulo, scripts) pasa a ser método de una clase o deja de existir. La medición se hace sobre `main`: el trabajo que todavía vive en otra rama no se refleja hasta que se integra.

### 4. Funciones fuera de clases (sueltas) — sin señales

- **Qué mide:** Funciones de nivel de módulo que no pertenecen a ninguna clase (en esta materia el comportamiento debe ser método de una clase).
- **Por qué importa:** Son la forma más directa de código procedural en un proyecto que debería ser orientado a objetos.
- **Tu resultado:** **0** función(es) fuera de clases

### 5. Clase demasiado grande (SRP) (god) — sin señales

- **Qué mide:** Clases con demasiadas líneas o métodos de LÓGICA. No cuentan los getters/setters triviales ni la cantidad de campos por sí sola.
- **Por qué importa:** Una clase que hace de todo cambia por muchos motivos y cuesta entenderla y probarla.
- **Tu resultado:** **0** clase(s) demasiado grande(s)

### 6. Baja cohesión (LCOM) (cohesion) — con señales

- **Qué mide:** Métodos y campos de una clase que casi no se relacionan entre sí.
- **Por qué importa:** Una unidad que cambia por motivos distintos y comparte poca información suele esconder más de una responsabilidad.
- **Tu resultado:** **1** clase(s) con baja cohesión
- **Para investigar la causa:**
    - ¿Qué métodos de la clase no comparten estado con el resto?
    - ¿Cambian por motivos distintos según la tarea?
    - ¿Se pensó su responsabilidad antes de escribirla, o se fue agregando?

### 7. Complejidad alta (complejidad) — sin señales

- **Qué mide:** Complejidad ciclomática: cantidad de caminos posibles dentro de un método o función (condicionales, bucles, casos).
- **Por qué importa:** Muchos caminos = difícil de seguir y de probar; suelen esconderse casos sin cubrir.
- **Tu resultado:** **0** método(s)/función(es) con complejidad alta

### 8. Métodos/funciones largos o con muchos parámetros (tamano) — con señales

- **Qué mide:** Tamaño de los métodos/funciones y cantidad de parámetros.
- **Por qué importa:** Una función que hace varias cosas cuesta leerla, nombrarla y reutilizarla.
- **Tu resultado:** **3** método(s)/función(es) largos o con muchos parámetros
- **Para investigar la causa:**
    - ¿La función creció por acumulación de pasos?
    - ¿Agrupa operaciones que podrían describirse por separado?
    - ¿Los parámetros representan una misma idea (un objeto) o cosas sueltas?

### 9. Acoplamiento alto (CBO) (acoplamiento) — sin señales

- **Qué mide:** Cuánto depende una clase de otros tipos del proyecto. No cuentan los tipos de la plataforma (biblioteca estándar, DOM, motor Godot) ni los métodos/propiedades propias.
- **Por qué importa:** Piezas muy enlazadas hacen que un cambio se propague y que sea difícil reemplazar partes.
- **Tu resultado:** **0** clase(s) muy acoplada(s)

### 10. Herencia profunda (DIT) (herencia) — sin señales

- **Qué mide:** Profundidad de la cadena de herencia de una clase.
- **Por qué importa:** Jerarquías muy profundas vuelven difícil saber de dónde viene cada comportamiento.
- **Tu resultado:** **0** clase(s) con herencia profunda

### 11. Switch sobre tipos (OCP) (ocp) — sin señales

- **Qué mide:** Cadenas de dispatch por tipo (`else if ... is` / `else if ... instanceof`, `switch` sobre tipo o `typeof`). Un `is Tipo variable` aislado (pattern matching / cast seguro) NO cuenta como switch.
- **Por qué importa:** Cada tipo nuevo obliga a tocar varios lugares, en vez de extender con una implementación nueva.
- **Tu resultado:** **0** clase(s) con switch sobre tipos

### 12. Interfaz con demasiados métodos (ISP) (isp) — sin señales

- **Qué mide:** Interfaces o clases abstractas con muchos métodos declarados.
- **Por qué importa:** Obliga a implementar operaciones que no se usan y acopla a los clientes a métodos que no necesitan.
- **Tu resultado:** **0** interfaz(es) con demasiados métodos

### 13. Override vacío o 'no implementado' (LSP) (lsp) — sin señales

- **Qué mide:** Métodos sobrescritos que quedan vacíos o lanzan 'no implementado'.
- **Por qué importa:** Suele indicar que la jerarquía no representa bien el problema y rompe la sustitución entre clases.
- **Tu resultado:** **0** override(s) vacío(s) o 'no implementado'

### 14. Dependencia de concretos y falta de abstracciones (DIP) (dip) — con señales

- **Qué mide:** Instanciación directa de clases **propias** concretas y ausencia de interfaces o clases abstractas pese al acoplamiento. No cuentan los structs ni los nodos/tipos del motor.
- **Por qué importa:** Depender de implementaciones concretas dificulta cambiar o probar una parte sin arrastrar las demás.
- **Tu resultado:** **4** dependencia(s) de concretos sin abstracciones
- **Para investigar la causa:**
    - ¿La clase crea por dentro a sus colaboradores en vez de recibirlos?
    - ¿Hay puntos de variación que convendría expresar como abstracción?
    - ¿Cómo se probaría esa clase cambiando una implementación?

### 15. Clase sin métodos (solo datos) (data) — sin señales

- **Qué mide:** Clases que solo acumulan campos y no tienen comportamiento.
- **Por qué importa:** Separar datos de comportamiento reparte por el proyecto la lógica que debería estar cerca de esos datos.
- **Tu resultado:** **0** clase(s) sin métodos (solo datos)

### 16. Campos públicos (encapsulación) (encapsulacion) — con señales

- **Qué mide:** Clases que exponen campos sin control de acceso.
- **Por qué importa:** Expone el estado interno y hace que cualquier cambio dependa de quién lo modifica desde afuera.
- **Tu resultado:** **7** clase(s) con campos públicos
- **Para investigar la causa:**
    - ¿Se accede al campo desde otras clases para evitar un método?
    - ¿Qué reglas deberían respetarse al modificar ese estado?
    - ¿Los campos públicos se usan más como configuración que como estado?

### 17. Clase solo con métodos estáticos (utilitaria) (static) — sin señales

- **Qué mide:** Clases donde todos los métodos son estáticos.
- **Por qué importa:** Es comportamiento procedural disfrazado de clase: no se instancia ni participa del modelo de objetos.
- **Tu resultado:** **0** clase(s) solo con métodos estáticos

## 5. Evolución por commit

Métricas calculadas sobre el estado de `main` en **cada commit** (25 commits con trabajo), del primero al último. La calidad usa el perfil `exigente`; el aporte de código lo mide RepoSense. El índice puede **bajar** al crecer el proyecto: se acumulan señales.

```mermaid
xychart-beta
    title "Índice POO/SOLID a lo largo de los commits"
    x-axis ["f7645c2", "62b1a6d", "b1e91df", "0211b91", "56a8f79", "fa3b047", "191ea3c", "e86fc1a", "580c966", "3cc7274", "78ab355", "b488da6", "61b98c8", "30f9976", "77e899d", "61fb4df", "9633dd2", "72b08f9", "fd73c60", "4249243", "bbdf447", "6be8a7e", "71eab34", "33021dd", "0845c5e"]
    y-axis "Índice" 0 --> 100
    line [0, 0, 0, 50, 58, 58, 38, 37, 33, 26, 26, 34, 33, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56]
```

| # | Fecha | Commit | Autor | LOC | Índice | %POO | Duplic. | Sueltas |
|---|---|---|---|---:|---:|---:|---:|---:|
| 1 | 2026-08-14 | `f7645c2` | LauMackinnon | 0 | 0 | 0% | 0.0% | 0 |
| 2 | 2026-08-14 | `62b1a6d` | mfmasi | 0 | 0 | 0% | 0.0% | 0 |
| 3 | 2026-08-14 | `b1e91df` | mfmasi | 0 | 0 | 0% | 0.0% | 0 |
| 4 | 2026-08-14 | `0211b91` | LauMackinnon | 76 | 50 | 26% | 0.0% | 0 |
| 5 | 2026-08-24 | `56a8f79` | dqmdz-etec | 107 | 58 | 34% | 0.0% | 0 |
| 6 | 2026-08-24 | `fa3b047` | dqmdz-etec | 107 | 58 | 34% | 0.0% | 0 |
| 7 | 2026-08-28 | `191ea3c` | mfmasi | 204 | 38 | 69% | 0.0% | 0 |
| 8 | 2026-09-07 | `e86fc1a` | LauMackinnon | 314 | 37 | 77% | 0.0% | 0 |
| 9 | 2026-09-28 | `580c966` | mfmasi | 417 | 33 | 84% | 0.0% | 0 |
| 10 | 2026-10-09 | `3cc7274` | LauMackinnon | 482 | 26 | 87% | 6.2% | 0 |
| 11 | 2026-10-09 | `78ab355` | mfmasi | 481 | 26 | 87% | 6.2% | 0 |
| 12 | 2026-10-09 | `b488da6` | LauMackinnon | 690 | 34 | 91% | 3.9% | 0 |
| 13 | 2026-10-16 | `61b98c8` | mfmasi | 1,032 | 33 | 94% | 2.4% | 0 |
| 14 | 2026-10-23 | `30f9976` | LauMackinnon | 1,159 | 56 | 95% | 0.0% | 0 |
| 15 | 2026-10-23 | `77e899d` | mfmasi | 1,195 | 56 | 95% | 0.0% | 0 |
| 16 | 2026-10-23 | `61fb4df` | mfmasi | 1,272 | 56 | 95% | 0.0% | 0 |
| 17 | 2026-10-23 | `9633dd2` | mfmasi | 1,285 | 56 | 95% | 0.0% | 0 |
| 18 | 2026-10-26 | `72b08f9` | mfmasi | 1,315 | 56 | 95% | 0.0% | 0 |
| 19 | 2026-10-30 | `fd73c60` | LauMackinnon | 1,318 | 56 | 95% | 0.0% | 0 |
| 20 | 2026-10-30 | `4249243` | mfmasi | 1,417 | 56 | 96% | 0.0% | 0 |
| 21 | 2026-10-30 | `bbdf447` | mfmasi | 1,417 | 56 | 96% | 0.0% | 0 |
| 22 | 2026-11-07 | `6be8a7e` | dqmdz-etec | 1,417 | 56 | 96% | 0.0% | 0 |
| 23 | 2026-11-07 | `71eab34` | dqmdz-etec | 1,417 | 56 | 96% | 0.0% | 0 |
| 24 | 2026-11-07 | `33021dd` | dqmdz-etec | 1,417 | 56 | 96% | 0.0% | 0 |
| 25 | 2026-11-07 | `0845c5e` | dqmdz-etec | 1,417 | 56 | 96% | 0.0% | 0 |

<!-- actividad-clases:inicio -->
## Actividad en días de clase

> Período evaluado **2026-08-14 → 2026-11-13** · **21 clases** (lunes, viernes) · días de clase del calendario, extendidos hasta la fecha de corte.
>
> `Commits` = commits de trabajo (no-merge); `Merges` = merges de PR (también son push). Los días sin actividad dicen **sin actividad**. El cuadro principal usa sólo las fechas de clase del calendario oficial.
>
> **Aportes fuera del calendario:** cada alumno tiene un cuadro aparte con las fechas no lectivas con actividad, desde **2026-08-14** hasta **2026-11-13** (ventana de aportes). Esas fechas no forman parte del cuadro principal, pero se muestran para no perder trabajo hecho en fines de semana, feriados o después del período.
>
> Se recorre `git log --all` sobre un mirror (todas las ramas y `refs/pull/*`), con deduplicación por commit. Fecha = fecha de autor en UTC-3. Los commits locales no pusheados no se ven. Si el trabajo fue en pareja y el commit es de otra persona, figura a nombre del autor del commit; para reflejarlo, usar `Co-authored-by` o una nota manual.
>
> Sección informativa: no modifica el semáforo de aprobación.

### LauMackinnon

**14/21 clases con actividad** · 19 commits · 16 merges

| Fecha | Marca | Repositorios | Commits / merges |
|---|:--:|---|--:|
| 2026-08-14 | **Sí** | Fisica_Cool | 2 / 2 |
| 2026-08-21 | **Sí** | Fisica_Cool | 1 / 0 |
| 2026-08-24 | **Sí** | Fisica_Cool | 1 / 1 |
| 2026-08-28 | **Sí** | Fisica_Cool | 1 / 2 |
| 2026-08-31 | **Sí** | Fisica_Cool | 1 / 0 |
| 2026-09-04 | **Sí** | Fisica_Cool | 4 / 2 |
| 2026-09-07 | **Sí** | Fisica_Cool | 2 / 1 |
| 2026-09-14 | No | sin actividad | 0 / 0 |
| 2026-09-25 | No | sin actividad | 0 / 0 |
| 2026-09-28 | No | sin actividad | 0 / 0 |
| 2026-10-02 | **Sí** | Fisica_Cool | 1 / 0 |
| 2026-10-05 | No | sin actividad | 0 / 0 |
| 2026-10-09 | **Sí** | Fisica_Cool | 1 / 2 |
| 2026-10-16 | No | sin actividad | 0 / 0 |
| 2026-10-19 | **Sí** | Fisica_Cool | 1 / 0 |
| 2026-10-23 | **Sí** | Fisica_Cool | 1 / 3 |
| 2026-10-26 | **Sí** | Fisica_Cool | 2 / 1 |
| 2026-10-30 | **Sí** | Fisica_Cool | 1 / 1 |
| 2026-11-02 | No | sin actividad | 0 / 0 |
| 2026-11-06 | No | sin actividad | 0 / 0 |
| 2026-11-13 | **Sí** | Fisica_Cool | 0 / 1 |

#### Aportes fuera del calendario de clases — LauMackinnon

_Sin aportes fuera del calendario de clases para LauMackinnon._

### mfmasi

**11/21 clases con actividad** · 14 commits · 19 merges

| Fecha | Marca | Repositorios | Commits / merges |
|---|:--:|---|--:|
| 2026-08-14 | **Sí** | Fisica_Cool | 2 / 2 |
| 2026-08-21 | No | sin actividad | 0 / 0 |
| 2026-08-24 | **Sí** | Fisica_Cool | 1 / 0 |
| 2026-08-28 | **Sí** | Fisica_Cool | 1 / 1 |
| 2026-08-31 | No | sin actividad | 0 / 0 |
| 2026-09-04 | No | sin actividad | 0 / 0 |
| 2026-09-07 | No | sin actividad | 0 / 0 |
| 2026-09-14 | No | sin actividad | 0 / 0 |
| 2026-09-25 | No | sin actividad | 0 / 0 |
| 2026-09-28 | **Sí** | Fisica_Cool | 1 / 1 |
| 2026-10-02 | No | sin actividad | 0 / 0 |
| 2026-10-05 | No | sin actividad | 0 / 0 |
| 2026-10-09 | **Sí** | Fisica_Cool | 1 / 3 |
| 2026-10-16 | **Sí** | Fisica_Cool | 1 / 1 |
| 2026-10-19 | No | sin actividad | 0 / 0 |
| 2026-10-23 | **Sí** | Fisica_Cool | 3 / 5 |
| 2026-10-26 | **Sí** | Fisica_Cool | 1 / 1 |
| 2026-10-30 | **Sí** | Fisica_Cool | 2 / 2 |
| 2026-11-02 | **Sí** | Fisica_Cool | 1 / 2 |
| 2026-11-06 | No | sin actividad | 0 / 0 |
| 2026-11-13 | **Sí** | Fisica_Cool | 0 / 1 |

#### Aportes fuera del calendario de clases — mfmasi

_Sin aportes fuera del calendario de clases para mfmasi._

<!-- actividad-clases:fin -->

---
_Informe generado por la cátedra. Los criterios y umbrales de medición corresponden al perfil `exigente` de `calidad.py`; el semáforo de aprobación, a `CRITERIOS-CALIDAD.md` (ver `README.md`). Ante dudas, revisá el archivo señalado._
<!-- evaluacion:fin -->
