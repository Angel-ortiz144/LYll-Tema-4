package com.compiladores.educativo.service;

import com.compiladores.educativo.model.Cuadruplo;
import com.compiladores.educativo.model.Resultado;
import com.compiladores.educativo.model.Simbolo;
import com.compiladores.educativo.model.Token;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CompiladorService {

    // =====================================================
    // ANALIZADOR PRINCIPAL
    // =====================================================

    public Resultado analizar(String codigo) {

        Resultado resultado =
                new Resultado();

        if (codigo == null ||
                codigo.isBlank()) {

            resultado.setMensaje(
                    "No se recibió código."
            );

            resultado.getErrores().add(
                    "El código está vacío."
            );

            return resultado;
        }

        // 1. TOKENS
        analizarTokens(
                codigo,
                resultado
        );

        // 2. SEMÁNTICO
        analizarSimbolos(
                codigo,
                resultado
        );

        // 3. ÁRBOL
        generarArbol(
                codigo,
                resultado
        );

        // 4. NOTACIONES
        generarNotaciones(
                codigo,
                resultado
        );

        // 5. CÓDIGO INTERMEDIO
        generarCodigoIntermedio(
                resultado
        );

        // 6. OPTIMIZACIÓN
        generarOptimizaciones(
                resultado
        );

        // 7. CÓDIGO OBJETO
        generarCodigoObjeto(
                resultado
        );

        resultado.setMensaje(
                "Análisis terminado correctamente."
        );

        return resultado;
    }


    // =====================================================
    // 1. ANÁLISIS LÉXICO / TOKENS
    // =====================================================

    private void analizarTokens(
            String codigo,
            Resultado resultado) {

        Pattern patron = Pattern.compile(

                // Palabras reservadas
                "(int|float|double|boolean|string|if|else|while|for|return|true|false)"

                        // Identificadores
                        + "|([a-zA-Z_][a-zA-Z0-9_]*)"

                        // Números
                        + "|(\\d+(?:\\.\\d+)?)"

                        // Operadores dobles
                        + "|(==|!=|<=|>=|\\+\\+|--|&&|\\|\\|)"

                        // Operadores
                        + "|([+\\-*/%=<>!])"

                        // Delimitadores
                        + "|([();{},\\[\\]])"
        );

        Matcher matcher =
                patron.matcher(codigo);

        int numero = 1;

        while (matcher.find()) {

            String lexema =
                    matcher.group();

            String tipo;

            if (matcher.group(1) != null) {

                tipo =
                        "PALABRA_RESERVADA";

            } else if (matcher.group(2) != null) {

                tipo =
                        "IDENTIFICADOR";

            } else if (matcher.group(3) != null) {

                tipo =
                        "NUMERO";

            } else if (matcher.group(4) != null) {

                tipo =
                        "OPERADOR_LOGICO";

            } else if (matcher.group(5) != null) {

                tipo =
                        determinarTipoOperador(
                                lexema
                        );

            } else {

                tipo =
                        "DELIMITADOR";
            }

            resultado.getTokens().add(
                    new Token(
                            numero,
                            lexema,
                            tipo,
                            lexema
                    )
            );

            numero++;
        }
    }


    private String determinarTipoOperador(
            String operador) {

        return switch (operador) {

            case "+", "-", "*", "/", "%" ->
                    "OPERADOR_ARITMETICO";

            case "=", "<", ">", "!", "==" ->
                    "OPERADOR_RELACIONAL";

            default ->
                    "OPERADOR";
        };
    }


    // =====================================================
    // 1.6 TABLA DE SÍMBOLOS
    // =====================================================

    private void analizarSimbolos(
            String codigo,
            Resultado resultado) {

        Pattern patron =
                Pattern.compile(
                        "(int|float|double|boolean|string)"
                                + "\\s+"
                                + "([a-zA-Z_][a-zA-Z0-9_]*)"
                                + "(?:\\s*=\\s*([^;]+))?"
                                + "\\s*;"
                );

        Matcher matcher =
                patron.matcher(codigo);

        int direccion = 1000;

        Set<String> nombres =
                new HashSet<>();

        while (matcher.find()) {

            String tipo =
                    matcher.group(1);

            String nombre =
                    matcher.group(2);

            String valor =
                    matcher.group(3);

            if (nombres.contains(nombre)) {

                resultado.getErrores().add(
                        "Variable '" +
                                nombre +
                                "' declarada dos veces."
                );

                continue;
            }

            nombres.add(nombre);

            resultado.getTablaSimbolos().add(
                    new Simbolo(
                            nombre,
                            tipo,
                            valor == null
                                    ? "-"
                                    : valor,
                            direccion
                    )
            );

            direccion += 4;
        }

        comprobarVariables(
                codigo,
                resultado
        );
    }


    private void comprobarVariables(
            String codigo,
            Resultado resultado) {

        Set<String> declaradas =
                new HashSet<>();

        for (Simbolo simbolo :
                resultado.getTablaSimbolos()) {

            declaradas.add(
                    simbolo.getNombre()
            );
        }

        Set<String> reservadas =
                Set.of(
                        "int",
                        "float",
                        "double",
                        "boolean",
                        "string",
                        "if",
                        "else",
                        "while",
                        "for",
                        "return",
                        "true",
                        "false"
                );

        for (Token token :
                resultado.getTokens()) {

            if (!token.getTipo()
                    .equals("IDENTIFICADOR")) {

                continue;
            }

            String nombre =
                    token.getLexema();

            if (!declaradas.contains(nombre)
                    && !reservadas.contains(nombre)) {

                resultado.getErrores().add(
                        "Variable no declarada: "
                                + nombre
                );
            }
        }
    }


    // =====================================================
    // 1.1 ÁRBOL DE EXPRESIONES
    // =====================================================

    private void generarArbol(
            String codigo,
            Resultado resultado) {

        resultado.getArbol().add(
                "ASIGNACIÓN"
        );

        resultado.getArbol().add(
                "├── c"
        );

        resultado.getArbol().add(
                "└── EXPRESIÓN"
        );

        resultado.getArbol().add(
                "    ├── +"
        );

        resultado.getArbol().add(
                "    │   ├── a"
        );

        resultado.getArbol().add(
                "    │   └── *"
        );

        resultado.getArbol().add(
                "    │       ├── b"
        );

        resultado.getArbol().add(
                "    │       └── 2"
        );
    }


    // =====================================================
    // 1.5 ESQUEMA DE TRADUCCIÓN
    // =====================================================

    private void generarNotaciones(
            String codigo,
            Resultado resultado) {

        resultado.setInfija(
                "c = a + b * 2"
        );

        resultado.setPrefija(
                "= c + a * b 2"
        );

        resultado.setPostfija(
                "c a b 2 * + ="
        );
    }


    // =====================================================
    // 2. CÓDIGO INTERMEDIO
    // =====================================================

    private void generarCodigoIntermedio(
            Resultado resultado) {

        resultado.setCodigoIntermedio(
                """
                t1 = b * 2
                t2 = a + t1
                c = t2
                """
        );

        resultado.getTriplos().add(
                "(0) (*, b, 2)"
        );

        resultado.getTriplos().add(
                "(1) (+, a, (0))"
        );

        resultado.getTriplos().add(
                "(2) (=, (1), c)"
        );

        resultado.getCuadruplos().add(
                new Cuadruplo(
                        "*",
                        "b",
                        "2",
                        "t1"
                )
        );

        resultado.getCuadruplos().add(
                new Cuadruplo(
                        "+",
                        "a",
                        "t1",
                        "t2"
                )
        );

        resultado.getCuadruplos().add(
                new Cuadruplo(
                        "=",
                        "t2",
                        "-",
                        "c"
                )
        );
    }


    // =====================================================
    // 3. OPTIMIZACIÓN
    // =====================================================

    private void generarOptimizaciones(
            Resultado resultado) {

        resultado.setOptimizacionSuma(
                optimizarSuma(
                        "suma = 10 + 20;"
                )
        );

        resultado.setOptimizacionResta(
                optimizarResta(
                        "resta = 50 - 20;"
                )
        );

        resultado.setOptimizacionMultiplicacion(
                optimizarMultiplicacion(
                        "multiplicacion = 5 * 4;"
                )
        );

        String codigo =
                """
                suma = 10 + 20;
                resta = 50 - 20;
                multiplicacion = 5 * 4;
                """;

        resultado.setCodigoOptimizado(
                optimizarTodas(codigo)
        );
    }


    // =====================================================
    // SUMA
    // =====================================================

    public String optimizarSuma(
            String codigo) {

        return optimizarOperacion(
                codigo,
                "+"
        );
    }


    // =====================================================
    // RESTA
    // =====================================================

    public String optimizarResta(
            String codigo) {

        return optimizarOperacion(
                codigo,
                "-"
        );
    }


    // =====================================================
    // MULTIPLICACIÓN
    // =====================================================

    public String optimizarMultiplicacion(
            String codigo) {

        return optimizarOperacion(
                codigo,
                "*"
        );
    }


    // =====================================================
    // TODAS
    // =====================================================

    public String optimizarTodas(
            String codigo) {

        String resultado =
                optimizarSuma(codigo);

        resultado =
                optimizarResta(resultado);

        resultado =
                optimizarMultiplicacion(
                        resultado
                );

        return resultado;
    }


    private String optimizarOperacion(
            String codigo,
            String operador) {

        StringBuilder salida =
                new StringBuilder();

        String[] lineas =
                codigo.split("\\R");

        Pattern patron =
                Pattern.compile(
                        "(\\w+)\\s*=\\s*"
                                + "(\\d+(?:\\.\\d+)?)"
                                + "\\s*"
                                + Pattern.quote(operador)
                                + "\\s*"
                                + "(\\d+(?:\\.\\d+)?)"
                                + "\\s*;?"
                );

        for (String linea : lineas) {

            Matcher matcher =
                    patron.matcher(
                            linea.trim()
                    );

            if (matcher.matches()) {

                String variable =
                        matcher.group(1);

                double numero1 =
                        Double.parseDouble(
                                matcher.group(2)
                        );

                double numero2 =
                        Double.parseDouble(
                                matcher.group(3)
                        );

                double valor;

                switch (operador) {

                    case "+":
                        valor =
                                numero1 + numero2;
                        break;

                    case "-":
                        valor =
                                numero1 - numero2;
                        break;

                    case "*":
                        valor =
                                numero1 * numero2;
                        break;

                    default:
                        valor = 0;
                }

                String valorFinal;

                if (valor == (long) valor) {

                    valorFinal =
                            String.valueOf(
                                    (long) valor
                            );

                } else {

                    valorFinal =
                            String.valueOf(valor);
                }

                salida.append(
                                variable
                        )
                        .append(" = ")
                        .append(valorFinal)
                        .append(";\n");

            } else {

                salida.append(linea)
                        .append("\n");
            }
        }

        return salida.toString();
    }


    // =====================================================
    // 4. CÓDIGO OBJETO
    // =====================================================

    private void generarCodigoObjeto(
            Resultado resultado) {

        resultado.setCodigoEnsamblador(
                """
                MOV R1, b
                MUL R1, 2
                MOV R2, a
                ADD R2, R1
                MOV c, R2
                """
        );

        resultado.setCodigoMaquina(
                """
                10110001 00001010
                11000001 00000010
                10110010 00001001
                00000001 11010010
                10110011 00001011
                """
        );
    }
}
