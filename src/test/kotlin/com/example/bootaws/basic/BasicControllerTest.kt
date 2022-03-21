package com.example.bootaws.basic

import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BasicControllerTest(
    private val mvc: MockMvc
){

    @Test
    @Order(1)
    fun `Basic Controller Mock 테스트 - get by id`(){
        // given
        val uri = "/api/v1/basic/get/1"

        // when
        val result = mvc.perform(get(uri))

        // then
        result.andExpect(status().isOk)
    }
}