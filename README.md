# Compiler for MicroJava

The objective of this project is to implement a compiler for the programming language MicroJava. The compiler enables the translation of syntactically and semantically correct MicroJava programs into MicroJava bytecode, which is executed on the MicroJava virtual machine. Syntactically and semantically correct MicroJava programs are defined by the [MJ] specification.

The MicroJava compiler has four core functionalities: lexical analysis, syntax analysis, semantic analysis, and code generation.

Lexical Analysis: The lexical analyzer recognizes language lexemes and returns a set of tokens extracted from the source code, which are further processed in syntax analysis. If a lexical error is detected during lexical analysis, an appropriate message is output.

Syntax Analysis: The syntax analyzer’s task is to determine whether the tokens extracted from the source code can form grammatically correct sentences. After parsing syntactically correct MicroJava programs, the user is informed of the successful parsing. If the source code contains syntax errors, an adequate explanation of the detected syntax error is provided, error recovery is performed, and parsing continues.

Semantic Analysis: The semantic analyzer is formed based on the abstract syntax tree generated as a result of syntax analysis. Semantic analysis is conducted by implementing methods to visit nodes of the abstract syntax tree. The tree is formed based on the grammar implemented in the previous phase. If the source code contains semantic errors, an appropriate message about the detected semantic error is displayed.

Code Generation: The code generator translates syntactically and semantically correct programs into an executable format for the selected MicroJava VM environment. Code generation is implemented in a similar way to semantic analysis, by implementing methods that visit nodes.

Each of these phases is essential to ensure that the final output is valid MicroJava bytecode, ready to be executed in the intended virtual environment.