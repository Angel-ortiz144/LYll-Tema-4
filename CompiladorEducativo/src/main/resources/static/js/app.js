let resultadoGlobal = null;


// ======================================================
// ANALIZAR
// ======================================================

async function analizar() {

    const codigo =
        document.getElementById(
            "codigo"
        ).value;

    const mensaje =
        document.getElementById(
            "mensaje"
        );

    if (!codigo.trim()) {

        mensaje.innerText =
            "Escribe código primero.";

        return;
    }

    mensaje.innerText =
        "Analizando código...";

    try {

        const respuesta =
            await fetch(
                "/api/compilador/analizar",
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "text/plain"
                    },

                    body: codigo
                }
            );

        if (!respuesta.ok) {

            throw new Error(
                "Error del servidor."
            );
        }

        resultadoGlobal =
            await respuesta.json();

        mensaje.innerText =
            resultadoGlobal.mensaje;

        mostrar("semantico");

    } catch (error) {

        mensaje.innerText =
            "Error: " +
            error.message;
    }
}


// ======================================================
// CAMBIAR MÓDULO
// ======================================================

function mostrar(modulo) {

    if (!resultadoGlobal) {

        document.getElementById(
            "contenido"
        ).innerHTML = `

            <div class="card">

                <h3>
                    Información
                </h3>

                <p>
                    Primero presiona
                    ANALIZAR.
                </p>

            </div>

        `;

        return;
    }

    switch (modulo) {

        case "semantico":
            mostrarSemantico();
            break;

        case "intermedio":
            mostrarIntermedio();
            break;

        case "optimizacion":
            mostrarOptimizacion();
            break;

        case "objeto":
            mostrarObjeto();
            break;
    }
}


// ======================================================
// 1. ANÁLISIS SEMÁNTICO
// ======================================================

function mostrarSemantico() {

    const r =
        resultadoGlobal;

    let html = "";


    // --------------------------------------------------
    // TOKENS
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                🔤 1.0 Tokens
            </h3>

            <p>
                Resultado del análisis léxico.
            </p>

            <br>

            <table>

                <thead>

                    <tr>

                        <th>
                            #
                        </th>

                        <th>
                            Lexema
                        </th>

                        <th>
                            Tipo
                        </th>

                        <th>
                            Valor
                        </th>

                    </tr>

                </thead>

                <tbody>

                    ${
        r.tokens
            .map(
                token => `

                                    <tr>

                                        <td>
                                            ${token.numero}
                                        </td>

                                        <td>
                                            <strong>
                                                ${token.lexema}
                                            </strong>
                                        </td>

                                        <td>
                                            ${token.tipo}
                                        </td>

                                        <td>
                                            ${token.valor}
                                        </td>

                                    </tr>

                                `
            )
            .join("")
    }

                </tbody>

            </table>

        </div>

    `;


    // --------------------------------------------------
    // ÁRBOL
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                1.1 Árbol de expresiones
            </h3>

            <pre>${r.arbol.join("\n")}</pre>

        </div>

    `;


    // --------------------------------------------------
    // ACCIONES SEMÁNTICAS
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                1.2 Acciones semánticas
            </h3>

            <pre>
DECLARACIÓN
    ↓
Registrar símbolo

ASIGNACIÓN
    ↓
Comprobar variable

EXPRESIÓN
    ↓
Comprobar operandos

RESULTADO
    ↓
Generar código intermedio
            </pre>

        </div>

    `;


    // --------------------------------------------------
    // TIPOS
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                1.3 Comprobación de tipos
            </h3>

            <pre>
int + int       → int
float + float   → float
int * int       → int
int - int       → int
            </pre>

        </div>

    `;


    // --------------------------------------------------
    // PILA
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                1.4 Pila semántica
            </h3>

            <pre>
PUSH a
PUSH b
PUSH 2
MUL
ADD
POP c
            </pre>

        </div>

    `;


    // --------------------------------------------------
    // ESQUEMA
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                1.5 Esquema de traducción
            </h3>

            <pre>
Código fuente
      ↓
Tokens
      ↓
Análisis sintáctico
      ↓
Análisis semántico
      ↓
Código intermedio
      ↓
Optimización
      ↓
Código objeto
            </pre>

        </div>

    `;


    // --------------------------------------------------
    // TABLA DE SÍMBOLOS
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                1.6 Tabla de símbolos
            </h3>

            <table>

                <tr>

                    <th>
                        Nombre
                    </th>

                    <th>
                        Tipo
                    </th>

                    <th>
                        Valor
                    </th>

                    <th>
                        Dirección
                    </th>

                </tr>

                ${
        r.tablaSimbolos
            .map(
                simbolo => `

                                <tr>

                                    <td>
                                        ${simbolo.nombre}
                                    </td>

                                    <td>
                                        ${simbolo.tipo}
                                    </td>

                                    <td>
                                        ${simbolo.valor}
                                    </td>

                                    <td>
                                        ${simbolo.direccion}
                                    </td>

                                </tr>

                            `
            )
            .join("")
    }

            </table>

        </div>

    `;


    // --------------------------------------------------
    // ERRORES
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                1.7 Manejo de errores semánticos
            </h3>

            ${
        r.errores.length === 0

            ?

            `
                    <p class="success">
                        ✓ No se encontraron
                        errores semánticos.
                    </p>
                `

            :

            r.errores
                .map(
                    error => `

                            <p class="error">
                                ❌ ${error}
                            </p>

                        `
                )
                .join("")
    }

        </div>

    `;


    document.getElementById(
        "contenido"
    ).innerHTML = html;
}


// ======================================================
// 2. CÓDIGO INTERMEDIO
// ======================================================

function mostrarIntermedio() {

    const r =
        resultadoGlobal;

    let html = "";


    // --------------------------------------------------
    // NOTACIONES
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                2.1 Notaciones
            </h3>

            <h4>
                2.1.1 Prefija
            </h4>

            <pre>
${r.prefija}
            </pre>

            <h4>
                2.1.2 Infija
            </h4>

            <pre>
${r.infija}
            </pre>

            <h4>
                2.1.3 Postfija
            </h4>

            <pre>
${r.postfija}
            </pre>

        </div>

    `;


    // --------------------------------------------------
    // POLACA
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                2.2.1 Notación Polaca
            </h3>

            <pre>
${r.prefija}
            </pre>

        </div>

    `;


    // --------------------------------------------------
    // CÓDIGO P
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                2.2.2 Código P
            </h3>

            <pre>
LOD a
LOD b
LDC 2
MUL
ADD
STO c
            </pre>

        </div>

    `;


    // --------------------------------------------------
    // TRIPLOS
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                2.2.3 Triplos
            </h3>

            <pre>
${r.triplos.join("\n")}
            </pre>

        </div>

    `;


    // --------------------------------------------------
    // CUÁDRUPLOS
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                2.2.4 Cuádruplos
            </h3>

            <table>

                <tr>

                    <th>
                        Operador
                    </th>

                    <th>
                        Argumento 1
                    </th>

                    <th>
                        Argumento 2
                    </th>

                    <th>
                        Resultado
                    </th>

                </tr>

                ${
        r.cuadruplos
            .map(
                q => `

                                <tr>

                                    <td>
                                        ${q.operador}
                                    </td>

                                    <td>
                                        ${q.argumento1}
                                    </td>

                                    <td>
                                        ${q.argumento2}
                                    </td>

                                    <td>
                                        ${q.resultado}
                                    </td>

                                </tr>

                            `
            )
            .join("")
    }

            </table>

        </div>

    `;


    // --------------------------------------------------
    // GENERACIÓN
    // --------------------------------------------------

    html += `

        <div class="card">

            <h3>
                2.3 Esquema de generación
            </h3>

            <h4>
                Variables y constantes
            </h4>

            <pre>
a = 10
b = 20
            </pre>

            <h4>
                Expresiones
            </h4>

            <pre>
t1 = b * 2
t2 = a + t1
            </pre>

            <h4>
                Instrucción de asignación
            </h4>

            <pre>
c = t2
            </pre>

            <h4>
                Control
            </h4>

            <pre>
IF condición
GOTO etiqueta
            </pre>

            <h4>
                Funciones
            </h4>

            <pre>
PARAM a
CALL funcion
            </pre>

            <h4>
                Estructuras
            </h4>

            <pre>
IF
WHILE
FOR
            </pre>

        </div>

    `;


    document.getElementById(
        "contenido"
    ).innerHTML = html;
}


// ======================================================
// 3. OPTIMIZACIÓN
// ======================================================

function mostrarOptimizacion() {

    const r =
        resultadoGlobal;

    document.getElementById(
        "contenido"
    ).innerHTML = `

        <div class="card">

            <h3>
                3. Optimización
            </h3>

            <p>
                Selecciona una operación
                para aplicar evaluación
                de constantes.
            </p>

        </div>


        <div class="optimizacion-grid">

            <div class="operacion">

                <h4>
                    ➕ Suma
                </h4>

                <p>
                    Ejemplo:
                    10 + 20 → 30
                </p>

                <br>

                <button
                        onclick="optimizarSuma()">

                    Optimizar Suma

                </button>

            </div>


            <div class="operacion">

                <h4>
                    ➖ Resta
                </h4>

                <p>
                    Ejemplo:
                    50 - 20 → 30
                </p>

                <br>

                <button
                        onclick="optimizarResta()">

                    Optimizar Resta

                </button>

            </div>


            <div class="operacion">

                <h4>
                    ✖️ Multiplicación
                </h4>

                <p>
                    Ejemplo:
                    5 * 4 → 20
                </p>

                <br>

                <button
                        onclick="optimizarMultiplicacion()">

                    Optimizar Multiplicación

                </button>

            </div>


            <div class="operacion">

                <h4>
                    ⚡ Todas
                </h4>

                <p>
                    Aplica todas las
                    optimizaciones.
                </p>

                <br>

                <button
                        onclick="optimizarTodas()">

                    Optimizar Todas

                </button>

            </div>

        </div>


        <div class="card">

            <h3>
                3.1 Tipos de optimización
            </h3>

            <ul>

                <li>
                    3.1.1 Optimización local
                </li>

                <li>
                    3.1.2 Optimización de ciclos
                </li>

                <li>
                    3.1.3 Optimización global
                </li>

                <li>
                    3.1.4 Optimización de mirilla
                </li>

            </ul>

        </div>


        <div class="card">

            <h3>
                3.2 Costos
            </h3>

            <pre>
3.2.1 Costo de ejecución

Memoria
Registros
Pila
Tiempo de ejecución

3.2.2 Criterios

Menos instrucciones
Menor uso de memoria
Menor número de operaciones

3.2.3 Herramientas

Análisis de código
Tabla de símbolos
Código intermedio
Código objeto
            </pre>

        </div>


        <div class="card">

            <h3>
                Código optimizado
            </h3>

            <pre>
${r.codigoOptimizado}
            </pre>

        </div>

    `;
}


// ======================================================
// OPTIMIZAR SUMA
// ======================================================

async function optimizarSuma() {

    const codigo =
        document.getElementById(
            "codigo"
        ).value;

    const resultado =
        await enviarOptimizacion(
            "suma",
            codigo
        );

    mostrarOperacion(
        "➕ Optimización de Suma",
        codigo,
        resultado
    );
}


// ======================================================
// OPTIMIZAR RESTA
// ======================================================

async function optimizarResta() {

    const codigo =
        document.getElementById(
            "codigo"
        ).value;

    const resultado =
        await enviarOptimizacion(
            "resta",
            codigo
        );

    mostrarOperacion(
        "➖ Optimización de Resta",
        codigo,
        resultado
    );
}


// ======================================================
// OPTIMIZAR MULTIPLICACIÓN
// ======================================================

async function optimizarMultiplicacion() {

    const codigo =
        document.getElementById(
            "codigo"
        ).value;

    const resultado =
        await enviarOptimizacion(
            "multiplicacion",
            codigo
        );

    mostrarOperacion(
        "✖️ Optimización de Multiplicación",
        codigo,
        resultado
    );
}


// ======================================================
// OPTIMIZAR TODAS
// ======================================================

async function optimizarTodas() {

    const codigo =
        document.getElementById(
            "codigo"
        ).value;

    const resultado =
        await enviarOptimizacion(
            "todas",
            codigo
        );

    mostrarOperacion(
        "⚡ Optimización Completa",
        codigo,
        resultado
    );
}


// ======================================================
// PETICIÓN DE OPTIMIZACIÓN
// ======================================================

async function enviarOptimizacion(
    tipo,
    codigo) {

    try {

        const respuesta =
            await fetch(
                "/api/compilador/optimizar/"
                + tipo,
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "text/plain"
                    },

                    body: codigo
                }
            );

        if (!respuesta.ok) {

            throw new Error(
                "Error en optimización."
            );
        }

        return await respuesta.text();

    } catch (error) {

        return (
            "Error: " +
            error.message
        );
    }
}


// ======================================================
// MOSTRAR RESULTADO DE OPTIMIZACIÓN
// ======================================================

function mostrarOperacion(
    titulo,
    original,
    optimizado) {

    document.getElementById(
        "contenido"
    ).innerHTML = `

        <div class="card">

            <h3>
                ${titulo}
            </h3>

            <p class="success">
                ✓ Optimización realizada.
            </p>

        </div>


        <div class="card">

            <h3>
                Código original
            </h3>

            <pre>
${original}
            </pre>

        </div>


        <div class="card">

            <h3>
                Código optimizado
            </h3>

            <pre>
${optimizado}
            </pre>

        </div>

    `;
}


// ======================================================
// 4. CÓDIGO OBJETO
// ======================================================

function mostrarObjeto() {

    const r =
        resultadoGlobal;

    document.getElementById(
        "contenido"
    ).innerHTML = `

        <div class="card">

            <h3>
                4.1 Registros
            </h3>

            <pre>
R1
R2
R3
R4
            </pre>

        </div>


        <div class="card">

            <h3>
                4.2 Lenguaje ensamblador
            </h3>

            <pre>
${r.codigoEnsamblador}
            </pre>

        </div>


        <div class="card">

            <h3>
                4.3 Lenguaje máquina
            </h3>

            <pre>
${r.codigoMaquina}
            </pre>

        </div>


        <div class="card">

            <h3>
                4.4 Administración de memoria
            </h3>

            <table>

                <tr>

                    <th>
                        Variable
                    </th>

                    <th>
                        Tipo
                    </th>

                    <th>
                        Dirección
                    </th>

                </tr>

                <tr>

                    <td>
                        a
                    </td>

                    <td>
                        int
                    </td>

                    <td>
                        1000
                    </td>

                </tr>

                <tr>

                    <td>
                        b
                    </td>

                    <td>
                        int
                    </td>

                    <td>
                        1004
                    </td>

                </tr>

                <tr>

                    <td>
                        c
                    </td>

                    <td>
                        int
                    </td>

                    <td>
                        1008
                    </td>

                </tr>

                <tr>

                    <td>
                        t1
                    </td>

                    <td>
                        temporal
                    </td>

                    <td>
                        pila
                    </td>

                </tr>

                <tr>

                    <td>
                        t2
                    </td>

                    <td>
                        temporal
                    </td>

                    <td>
                        pila
                    </td>

                </tr>

            </table>

        </div>

    `;
}


// ======================================================
// EJEMPLO
// ======================================================

function ejemplo() {

    document.getElementById(
        "codigo"
    ).value = `int a = 10;
int b = 20;
int c;

c = a + b * 2;

suma = 10 + 20;
resta = 50 - 20;
multiplicacion = 5 * 4;`;

}


// ======================================================
// LIMPIAR
// ======================================================

function limpiar() {

    document.getElementById(
        "codigo"
    ).value = "";

    document.getElementById(
        "contenido"
    ).innerHTML = "";

    document.getElementById(
        "mensaje"
    ).innerText =
        "Editor limpio.";

    resultadoGlobal = null;
}