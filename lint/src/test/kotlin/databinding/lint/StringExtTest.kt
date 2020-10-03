package databinding.lint

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class StringExtTest {

    @Test
    fun testIsOneWayBinding() {
        val result = "@{viewModel.value}".isBinding()
        assertThat(result, equalTo(true))
    }

    @Test
    fun testIsTwoWayBinding() {
        val result = "@={viewModel.value}".isBinding()
        assertThat(result, equalTo(true))
    }

    @Test
    fun testIsNotABinding() {
        val result = "viewModel.value".isBinding()
        assertThat(result, equalTo(false))
    }

    @Test
    fun testEscapeOneWayBinding() {
        val expected = "viewModel.value"
        val result = "@{$expected}".escapeBinding()
        assertThat(result, equalTo(expected))
    }

    @Test
    fun testEscapeTwoWayBinding() {
        val expected = "viewModel.value"
        val result = "@={$expected}".escapeBinding()
        assertThat(result, equalTo(expected))
    }
}
