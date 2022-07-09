package io.github.parse

enum class Operator(val literal: String) {
    EQ("eq"), GT("gt"), NOT("not"), OR("or"), AND("and")
}

enum class ReTokenType {
    ID, ASSIGN, EQ, GT, NOT, AND, OR, LPAREN, RPAREN, NUMBER, STRING_VALUE, BRACKET, BRACE, COMMA, COLON, EOF, VALUE;

    fun isCalculationOperator() = this == EQ || this == GT
}

class PostToken(val value: String, val operator: ReTokenType) {


    override fun toString(): String {
        return "PostToken(value='$value', operator=$operator)"
    }
}

sealed interface Ast

data class Unary(val token: PostToken, val value: Ast) : Ast

data class Binary(val token: PostToken, val left: Ast, val right: Ast) : Ast

data class Literal(val value: PostToken) : Ast

data class Statement(val left: Ast, val op: ReTokenType, val right: Ast) : Ast

data class Compose(val left: Ast, val op: ReTokenType, val right: Ast) : Ast

data class Logic(val reTokenType: PostToken, val list: List<Ast>) : Ast

data class Var(val name: String) : Ast

class PostLexer(
    private val input: String,
    private var pos: Int = 0,
    private val charArray: CharArray = input.toCharArray(),
) {

    private val currentChar get() = charArray[pos]


    fun nextToken(): PostToken {
        while (pos <= charArray.lastIndex) {
            return when (currentChar) {
                ' ', '.' -> {
                    pos++
                    continue
                }
                ',' -> comma()
                '"' -> stringVar()
                '(' -> lParen()
                ')' -> rParen()
                '=' -> assign()
                'e' -> eqOrElse()
                'g' -> gtOrElse()
                'n' -> notOrElse()
                'a' -> andOrElse()
                'o' -> orOrElse()
                else -> commonValue()
            }
        }
        return PostToken("", ReTokenType.EOF)
    }

    private fun comma(): PostToken {
        advance()
        return PostToken(",", ReTokenType.COMMA)
    }


    private fun orOrElse(): PostToken {
        return if (peek() == 'r' && peek(2) == '(') {
            advance(2)
            PostToken("or", ReTokenType.OR)
        } else {
            commonValue()
        }
    }

    private fun andOrElse(): PostToken {
        return if (peek() == 'n' && peek(2) == 'd' && peek(3) == '(') {
            advance(3)
            PostToken("and", ReTokenType.AND)
        } else {
            commonValue()
        }
    }

    private fun notOrElse(): PostToken {
        return if (peek() == 'o' && peek(2) == 't' && peek(3) == '.') {
            advance(4)
            PostToken("not", ReTokenType.NOT)
        } else {
            commonValue()
        }

    }

    private fun gtOrElse(): PostToken {
        return if (peek() == 't' && peek(2) == '.') {
            advance(3)
            PostToken("gt", ReTokenType.GT)
        } else {
            commonValue()
        }
    }

    private fun assign(): PostToken {
        advance()
        return PostToken("=", ReTokenType.ASSIGN)
    }


    private fun eqOrElse(): PostToken {
        return if (peek() == 'q' && peek(2) == '.') {
            advance(3)
            PostToken("eq", ReTokenType.EQ)
        } else {
            commonValue()
        }
    }

    private fun commonValue(): PostToken = PostToken(
        buildString {
            while (pos <= charArray.lastIndex && (currentChar.isLetterOrDigit())) {
                this + currentChar
                advance()
            }
        },
        //包括变量和值
        ReTokenType.VALUE
    )


    private fun lParen(): PostToken {
        advance()
        return PostToken("(", ReTokenType.LPAREN)
    }

    private fun rParen(): PostToken {
        advance()
        return PostToken(")", ReTokenType.RPAREN)
    }


    private fun stringVar() = PostToken(buildString {
        while (currentChar != '"') {
            this + currentChar
            advance()
        }
        advance()
    }, ReTokenType.ID)


    private fun advance(step: Int = 1) = pos.apply {
        pos += step
    }


    // peek a value to see is it an operator
    // 可能为空
    private fun peek(step: Int = 1) = charArray[pos + step]

}


//
// name="abcd"
// name=eq."abc"
// name=age.not.eq.123
//
// name=or("age".eq.or(18,22),and(address.eq."home",school.eq.ust=c))


// assignment_statement: variable = expr

//expr : expression  ( comma  expression ) *

// term: factor | calExpress | unaryOperator calExpress

// implicitTrans : variable calExpress | variable unaryOperator calExpress

//calExpress = calOperator factor

// calOperator = eq | gt

// unaryOperator = not

// factor = value  |  binaryExpression

// binaryExpression = ( and | or ) term ( semi term )*

// variable: ID

class RestParse(private val lexer: PostLexer) {

    private var currentToken = lexer.nextToken()

    private val nextToken = { lexer.nextToken() }

    private fun eatAndNext(tokenType: ReTokenType) {
        if (tokenType == currentToken.operator) {
            currentToken = nextToken()
        } else {
            throw IllegalArgumentException("$tokenType expected but ${currentToken.operator} found")
        }
    }


    /**
     * Parse的入口
     *
     * @return
     */
    fun parse(): Ast {
        val node = assignStatement()
        if (nextToken().operator != ReTokenType.EOF) {
            throw IllegalArgumentException("Expected EOF, but found ${nextToken().operator}")
        }
        return node
    }


    // assignment_statement: variable = expr
    private fun assignStatement(): Ast {
        val variable = currentToken.value
        eatAndNext(ReTokenType.VALUE)
        if (currentToken.operator != ReTokenType.ASSIGN) {
            throw IllegalArgumentException("Expected =, but found ${currentToken.operator}")
        }
        eatAndNext(ReTokenType.ASSIGN)
        return Statement(Var(variable), ReTokenType.ASSIGN, expression())
    }


    //expr : term  ( comma  term ) *
    private fun expression(): Ast {
        var node = term()
        while (currentToken.operator == ReTokenType.COMMA) {
            eatAndNext(ReTokenType.COMMA)
            node = Compose(node, ReTokenType.COMMA, term())
        }
        return node
    }

    // term: factor | calOperator factor | unaryOperator calOperator  factor
    private fun term(): Ast {
        if (currentToken.operator == ReTokenType.VALUE) {
            return factor()
        } else if (currentToken.operator.isCalculationOperator()) {
            val v = currentToken
            eatAndNext(currentToken.operator)
            return Unary(v, factor())
        } else if (currentToken.operator == ReTokenType.NOT) {
            eatAndNext(currentToken.operator)
            if (currentToken.operator.isCalculationOperator()) {
                eatAndNext(currentToken.operator)
                return Unary(currentToken, factor())
            } else {
                throw IllegalArgumentException("Expected calculation operator, but found ${currentToken.operator}")
            }
        } else {
            throw IllegalArgumentException("Expected value, but found ${currentToken.operator}")
        }
    }


    //factor = value  |  binaryExpression
    private fun factor(): Ast {
        return if (currentToken.operator == ReTokenType.VALUE) {
            val c = currentToken
            eatAndNext(ReTokenType.VALUE)
            Literal(c)
        } else {
            binaryExpression()
        }
    }


    //binaryExpression = ( and | or ) term ( semi term )*
    private fun binaryExpression(): Ast {
        val logicToken = currentToken
        if (currentToken.operator != ReTokenType.AND && currentToken.operator != ReTokenType.OR) {
            throw IllegalArgumentException("Expected AND or OR, but found ${currentToken.operator}")
        }
        eatAndNext(currentToken.operator)
        val logicList = buildList {
            add(term())
            while (currentToken.operator == ReTokenType.COMMA) {
                eatAndNext(ReTokenType.COMMA)
                add(term())
            }
        }
        return Logic(logicToken, logicList)
    }
}


fun Char.isEq() = this == '='

fun Char.isGt() = this == '>'

fun main() {
    val postLexer = PostLexer("name = li.not.eq.and(tao,shen), age.gt.38")
    println(RestParse(postLexer).parse())
}


/*
* todo refactor 把判断是否是保留保留字符通过枚举来实现
* language=python
    RESERVED_KEYWORDS = {
    'BEGIN': Token('BEGIN', 'BEGIN'),
    'END': Token('END', 'END'),

}

def _id(self):
"""Handle identifiers and reserved keywords"""
result = ''
while self.current_char is not None and self.current_char.isalnum():
result += self.current_char
self.advance()

token = RESERVED_KEYWORDS.get(result, Token(ID, result))
return token

* */


//
// name=abcd
// name=eq.abc
// name=age.not.eq.123
//
// name=or("age".eq.or(18,22),and(address.eq."home",school.eq.ust=c))

// calOperator : eq | gt
// factor: number| string | factor calOperator