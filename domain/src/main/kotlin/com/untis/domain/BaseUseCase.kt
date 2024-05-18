package com.untis.domain

/**
 * Base of all use cases
 *
 * @param Input The input type
 * @param Output The output type
 */
interface BaseUseCase<Input, Output> {

    /**
     * Runs the use case with the given [input]
     *
     * @param input The input
     * @return The output
     */
    operator fun invoke(input: Input): Output

}