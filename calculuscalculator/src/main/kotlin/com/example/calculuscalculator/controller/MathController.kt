package com.example.calculuscalculator.controller

import org.matheclipse.core.eval.ExprEvaluator
import org.matheclipse.core.interfaces.IExpr
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
@RestController
class MathController {

    private val evaluator = ExprEvaluator()

    @GetMapping("/evaluate")
    fun evaluate(@RequestParam expression: String): String {
        if (expression.isBlank()) {
            return "Expression cannot be empty"
        }
        // Check if the expression is valid
        if (!expression.matches(Regex("^[a-zA-Z0-9\\s+\\-*/()^]+$"))) {
            return "Invalid expression"
        }

       val numericExpression = "N($expression)"
val result: IExpr = evaluator.evaluate(numericExpression)
return result.toString()
    }

    @GetMapping("/differentiate")
    fun differentiate(@RequestParam expression: String, @RequestParam variable: String): String {
        if (expression.isBlank()) {
            return "Expression cannot be empty"
        }
        if (variable.isBlank()) {
            return "Variable cannot be empty"
        }
        val result: IExpr = evaluator.evaluate("D($expression, $variable)")
        return result.toString()
    }

    @GetMapping("/integrate")
    fun integrate(@RequestParam expression: String, @RequestParam variable: String): String {
        if (expression.isBlank()) {
            return "Expression cannot be empty"
        }
        if (variable.isBlank()) {
            return "Variable cannot be empty"
        }
        val result: IExpr = evaluator.evaluate("Integrate($expression, $variable)")
        return result.toString()
    }
}