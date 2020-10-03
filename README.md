# databinding-lint

[![CircleCI](https://img.shields.io/circleci/project/github/annypatel/databinding-lint/master.svg)](https://circleci.com/gh/annypatel/databinding-lint)
[![Release](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/com/github/annypatel/databinding/lint-checks/maven-metadata.xml.svg?label=release)](https://search.maven.org/search?q=g:com.github.annypatel.databinding)
![License](https://img.shields.io/github/license/annypatel/databinding-lint.svg)

The Data Binding Library allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically. Data Binding is a very powerful library and if you are not careful with it, you will end up writing lots of business logic in your XML files. `databinding-lint` library provides set of lint checks to detect binding expressions with such logic.

## Download
Download the latest artifact from Maven central at following coordinates:

```groovy
implementation 'com.github.annypatel.databinding:lint-checks:1.0.0-alpha2'
```

## Lint Checks

* **CastOperator** - Detects cast operation in binding expression.

	```
	res/layout/example.xml:5: Error: Cast operator in binding expression [CastOperator]
       android:text="@{(String) count}" />
       ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **UnaryOperator** - Detects usage of unary operators `+`, `-`, `!` and `~`.

	```
	res/layout/example.xml:5: Error: Unary operator in binding expression [UnaryOperator]
       android:text="@{+count}" />
       ~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **MathOperator** - Detects usage of mathematical operators `+`, `-`, `*`, `/` and `%`.

	```
	res/layout/example.xml:5: Error: Mathematical operator in binding expression [MathOperator]
	    android:text="@{width * height}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **BitShiftOperator** - Detects usage of bit-shift operators `<<`, `>>` and `>>>`.

	```
	res/layout/example.xml:5: Error: Bit-shift operator in binding expression [BitShiftOperator]
	    android:text="@{x >> 2}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **CompareOperator** - Detects usage of comparison operators `<`, `<=`, `>`, `>=`, `==` and `!=`.

	```
	res/layout/example.xml:5: Error: Comparison operator in binding expression [CompareOperator]
	    android:text="@{length > 0}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **InstanceOfOperator** - Detects usage of `instanceof` operator in binding expression.
	
	```
	res/layout/example.xml:5: Error: InstanceOf operator in binding expression [InstanceOfOperator]
	    android:text="@{data instanceof String}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **BinaryOperator** - Detects usage of binary operators `&`, `|` and `^`.

	```
	res/layout/example.xml:5: Error: Binary operator in binding expression [BinaryOperator]
	    android:text="@{a | b}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~
	```

* **AndOrOperator** - Detects usage of And-Or operators `&&` and `||`.

	```
	res/layout/example.xml:5: Error: AndOr operator in binding expression [AndOrOperator]
	    android:text="@{a || b}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **TernaryOperator** - Detects usage of ternary operator in binding expression.

	```
	res/layout/example.xml:5: Error: Ternary operator in binding expression [TernaryOperator]
	    android:text="@{a > b ? x : y}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **NullCoalescingOperator** - Detects usage of null coalescing `??` operator.

	```
	res/layout/example.xml:5: Error: Null Coalescing operator in binding expression [NullCoalescingOperator]
	    android:text="@{data ?? fallback}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **GroupOperator** - Detects usage of grouping `()` in binding expression.

	```
	res/layout/example.xml:5: Error: Group operator in binding expression [GroupOperator]
	    android:text="@{(width + height) / 2}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **BracketOperator** - Detects usage of bracket operator `[]` for accessing collections such as `list[index]` or `map[key]`.

	```
	res/layout/example.xml:5: Error: Bracket operator in binding expression [BracketOperator]
	    android:text="@{list[1]}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **ClassExtractionExpression** - Detects `.class` access in binding expression.

	```
	res/layout/example.xml:5: Error: Class extraction in binding expression [ClassExtractionExpression]
	    android:text="@{String.class}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **LiteralExpression** - Detects hardcoded java literals in binding expression.

	```
	res/layout/example.xml:5: Error: Literal in binding expression [LiteralExpression]
	    android:text="@{\"Data\"}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **MethodCallExpression** - Detects method calls in binding expressions.

	```
	res/layout/example.xml:5: Error: Method call in binding expression [MethodCallExpression]
	    android:text="@{String.valueOf(count)}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	```

* **GlobalMethodCallExpression** - Detects global method calls in binding expressions.

	```
	res/layout/example.xml:5: Error: Global method call in binding expression [GlobalMethodCallExpression]
	    android:text="@{valueOf(count)}" />
	    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	```
