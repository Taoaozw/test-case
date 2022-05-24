package io.github.parse


internal class Token(val type: TokenType, val value: String) {
    override fun toString(): String {
        return "$type:$value"
    }
}


class Interpreter(
    text: String,
    private var pos: Int = 0,
) {
    private val chars = text.toCharArray()

    private var currentToken: Token = nextToken()


    private val currentChar = { chars[pos] }

    private fun nextToken(): Token {
        while (pos <= chars.size - 1) {
            return when {
                chars[pos].isWhitespace() -> {
                    whiteSpace()
                    continue
                }
                chars[pos].isPlus() -> plus()
                chars[pos].isMinus() -> minus()
                chars[pos].isDigit() -> digit()
                chars[pos].isMulti() -> multi()
                chars[pos].isDiv() -> div()
                chars[pos].isLParen() -> lParen()
                chars[pos].isRParen() -> rParen()

                else -> throw TokenParseException(
                    "Unexpected character: ${chars[pos]} at position $pos in ${
                        String(
                            chars
                        )
                    }"
                )
            }
        }
        return Token(TokenType.EOF, "")
    }


    private fun whiteSpace() = pos++

    private fun plus() = advance(TokenType.PLUS)
    private fun minus() = advance(TokenType.MINUS)
    private fun multi() = advance(TokenType.MULTI)
    private fun div() = advance(TokenType.DIV)
    private fun lParen() = advance(TokenType.LPAREN)
    private fun rParen() = advance(TokenType.RPAREN)

    private fun digit(): Token {
        val build = StringBuilder()
        while (pos <= chars.lastIndex && chars[pos].isDigit()) {
            build.append(chars[pos])
            pos++
        }
        return Token(TokenType.INTEGER, build.toString())
    }

    private fun advance(type: TokenType): Token {
        pos++
        return Token(type, chars[pos - 1].toString())
    }


    private fun eat(vararg type: TokenType) {
        if (type.contains(currentToken.type)) {
            currentToken = nextToken()
        } else {
            throw TokenParseException("Expected $type but got ${currentToken.type} at position $pos in $chars")
        }
    }


    private fun factor(): Int {
        val token = currentToken
        return when (token.type) {
            TokenType.LPAREN -> {
                eat(TokenType.LPAREN)
                val result = expr()
                eat(TokenType.RPAREN)
                result
            }
            TokenType.INTEGER -> {
                eat(TokenType.INTEGER)
                token.value.toInt()
            }
            else -> throw TokenParseException("Expected INTEGER or LPAREN but got ${currentToken.type} at position $pos in $chars")
        }
    }

    private fun term(): Int {
        var left = factor()
        while (currentToken.type.onMultiOrDiv()) {
            val op = currentToken.type
            eat(op)
            val right = factor()
            left = when (op) {
                TokenType.MULTI -> left * right
                TokenType.DIV -> left / right
                else -> throw TokenParseException("Unexpected token ${currentToken.type}")
            }
        }
        return left
    }

    fun expr(): Int {
        var result: Int = term()
        while (currentToken.type in listOf(TokenType.PLUS, TokenType.MINUS)) {
            val op = currentToken
            eat(TokenType.PLUS, TokenType.MINUS)
            val right = term()
            result = when (op.type) {
                TokenType.PLUS -> result + right
                TokenType.MINUS -> result - right
                else -> throw TokenParseException("Unexpected token: $op")
            }
        }
        return result
    }
}

class TokenParseException(message: String) : Exception(message)

enum class TokenType {
    EOF, INTEGER, PLUS, MINUS, MULTI, DIV, LPAREN, RPAREN;

    fun onMultiOrDiv(): Boolean {
        return this == MULTI || this == DIV
    }
}

fun Char.isPlus(): Boolean = this == '+'
fun Char.isMinus(): Boolean = this == '-'
fun Char.isMulti(): Boolean = this == '*'
fun Char.isDiv(): Boolean = this == '/'
fun Char.isLParen(): Boolean = this == '('
fun Char.isRParen(): Boolean = this == ')'


fun main() {
    println(Interpreter("(11-6)*(3+2)").expr())
}