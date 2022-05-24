package io.github.parse

sealed interface AST


data class UnaryOp(val op: Token, val right: AST) : AST

data class BinOp(val left: AST, val op: Token, val right: AST) : AST


data class Num(val value: Int, val type: TokenType) : AST

operator fun StringBuilder.plus(i: Char) {
    append(i)
}

operator fun StringBuilder.plus(i: String) {
    append(i)
}

class Lexer(
    text: String,
    private var pos: Int = 0,
    private val array: CharArray = text.toCharArray()
) {

    private val currentChar = { array[pos] }

    fun nextToken(): Token {
        while (pos <= array.lastIndex) {
            return when {
                currentChar().isWhitespace() -> {
                    pos++
                    continue
                }
                currentChar().isDigit() -> digit()
                currentChar().isPlus() -> plus()
                currentChar().isMinus() -> minus()
                currentChar().isMul() -> mul()
                currentChar().isDiv() -> div()
                currentChar().isLParen() -> lParen()
                currentChar().isRParen() -> rParen()
                else -> throw IllegalArgumentException("${currentChar()} in $array pos: $pos")
            }
        }
        return Token(TokenType.EOF, "")
    }


    private fun digit() = Token(TokenType.INTEGER, buildString {
        while (pos <= array.lastIndex && currentChar().isDigit()) {
            this + currentChar()
            pos++
        }
    })

    private fun mul() = advance(TokenType.MUL)
    private fun div() = advance(TokenType.DIV)
    private fun plus() = advance(TokenType.PLUS)
    private fun minus() = advance(TokenType.MINUS)
    private fun lParen() = advance(TokenType.LPAREN)
    private fun rParen() = advance(TokenType.RPAREN)

    private fun advance(type: TokenType): Token {
        val token = Token(type, currentChar().toString())
        pos++
        return token
    }
}


class Parse(private val lexer: Lexer) {

    private var currentToken = lexer.nextToken()

    private val nextToken = { lexer.nextToken() }

    private fun eatAndNext(tokenType: TokenType) {
        if (tokenType == currentToken.type) {
            currentToken = nextToken()
        } else {
            throw IllegalArgumentException("$tokenType expected but ${currentToken.type} found")
        }
    }

    private fun factor(): AST {
        val tem = currentToken
        return when (currentToken.type) {
            TokenType.PLUS -> {
                eatAndNext(TokenType.PLUS)
                UnaryOp(tem, factor())
            }
            TokenType.MINUS -> {
                eatAndNext(TokenType.MINUS)
                UnaryOp(tem, factor())
            }
            TokenType.INTEGER -> {
                eatAndNext(TokenType.INTEGER)
                Num(tem.value.toInt(), tem.type)
            }
            TokenType.LPAREN -> {
                eatAndNext(TokenType.LPAREN)
                val result = expr()
                eatAndNext(TokenType.RPAREN)
                result
            }
            else -> throw IllegalArgumentException("factor expected but ${currentToken.type} found")
        }
    }


    private fun term(): AST {
        var left = factor()
        while (currentToken.type.onMultiOrDiv()) {
            val tempt = currentToken
            left = when (currentToken.type) {
                TokenType.MUL -> {
                    eatAndNext(TokenType.MUL)
                    BinOp(left, tempt, factor())
                }
                TokenType.DIV -> {
                    eatAndNext(TokenType.DIV)
                    BinOp(left, tempt, factor())
                }
                else -> throw IllegalArgumentException("${currentToken.type} expected but ${currentToken.type} found")
            }
        }
        return left
    }


    fun expr(): AST {
        var node = term()
        while (currentToken.type != TokenType.EOF && currentToken.type != TokenType.RPAREN) {
            node = when (currentToken.type) {
                TokenType.PLUS -> {
                    val tem = currentToken
                    eatAndNext(TokenType.PLUS)
                    BinOp(node, tem, term())
                }
                TokenType.MINUS -> {
                    val tem = currentToken
                    eatAndNext(TokenType.MINUS)
                    BinOp(node, tem, term())
                }
                else -> throw IllegalArgumentException("TokenType.PLUS and TokenType.MINUS expected but ${currentToken.type} found")
            }
        }
        return node
    }


}

interface NodeVisitor {

    fun visit(node: AST): Int
}

object MyInterpreter : NodeVisitor {


    override fun visit(node: AST): Int {
        return when (node) {
            is BinOp -> visitBiOp(node)
            is Num -> visitNum(node)
            is UnaryOp -> visitUnaryOp(node)
        }
    }

    private fun visitUnaryOp(node: UnaryOp): Int {
        return when (node.op.type) {
            TokenType.PLUS -> visit(node.right)
            TokenType.MINUS -> -visit(node.right)
            else -> throw IllegalArgumentException("visitUnaryOp only support PLUS or MINUS but ${node.op.type} found")
        }
    }


    private fun visitBiOp(node: BinOp): Int {
        return when (node.op.type) {
            TokenType.PLUS -> visit(node.left) + visit(node.right)
            TokenType.MINUS -> visit(node.left) - visit(node.right)
            TokenType.MUL -> visit(node.left) * visit(node.right)
            TokenType.DIV -> visit(node.left) / visit(node.right)
            else -> throw IllegalArgumentException("  ${node.op.type} type  is can't recognize")
        }

    }

    private fun visitNum(node: Num): Int {
        return node.value
    }
}


fun main() {
    val plusToken = Token(TokenType.PLUS, "+")

    val minusToken = Token(TokenType.MINUS, "-")

    val plusOp = BinOp(Num(1, TokenType.INTEGER), plusToken, Num(2, TokenType.INTEGER))

    val minusOp = BinOp(plusOp, minusToken, Num(2, TokenType.INTEGER))

    val lexer = Lexer("1*(--2+3*-3)+100")

    val message = Parse(lexer).expr()
    println(message)
    println(MyInterpreter.visit(message))
}


