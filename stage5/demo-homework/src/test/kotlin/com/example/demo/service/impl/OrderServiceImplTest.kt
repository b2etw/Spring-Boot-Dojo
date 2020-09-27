package com.example.demo.service.impl

import com.example.demo.core.ext.any
import com.example.demo.data.dto.OrderDto
import com.example.demo.data.entity.Item
import com.example.demo.data.entity.Order
import com.example.demo.data.entity.User
import com.example.demo.data.repository.ItemRepository
import com.example.demo.data.repository.OrderRepository
import com.example.demo.data.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.annotation.DirtiesContext
import java.time.LocalDateTime
import java.util.Optional

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderServiceImplTest {

    @MockBean
    lateinit var userRepository: UserRepository

    @MockBean
    lateinit var itemRepository: ItemRepository

    @MockBean
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var orderServiceImpl: OrderServiceImpl

    @Test
    fun testAddOrder() {
        val now = LocalDateTime.now()
        given(itemRepository.findById(1)).willReturn(Optional.of(Item(1, "soccer ball", 9.0f, now, now, "")))
        given(userRepository.findById(1)).willReturn(Optional.of(User(1, "Vincent", "Huang", 88)))
        given(orderRepository.save(any(Order::class.java))).willReturn(Order(1, 1, 1, 9.0f, 1, now, now, ""))

        val orderDto = OrderDto(-1, 1, "", 1, "", 9.0f, 1, now, now, "")
        val addOrder = orderServiceImpl.addOrder(orderDto)
        Assertions.assertEquals("soccer ball", addOrder.itemName)
        Assertions.assertEquals("Huang", addOrder.userLastName)
    }

    @Test
    fun testFindById() {
        val now = LocalDateTime.now()
        given(orderRepository.findDtoById(1)).willReturn(OrderDto(1, 1, "Huang", 1, "soccer ball", 10.0f, 2, now, now, ""))

        val orderDto = orderServiceImpl.findById(1)
        Assertions.assertEquals(10.0f, orderDto.price)
        Assertions.assertEquals(2, orderDto.amount)
    }

    @Test
    fun testModifyOrder() {
        val now = LocalDateTime.now()
        given(itemRepository.findById(1)).willReturn(Optional.of(Item(1, "soccer ball", 9.0f, now, now, "")))
        given(userRepository.findById(1)).willReturn(Optional.of(User(1, "Vincent", "Huang", 88)))
        given(orderRepository.findById(1)).willReturn(Optional.of(Order(1, 1, 1, 9.0f, 1, now, now, "")))
        given(orderRepository.save(any(Order::class.java))).willReturn(Order(1, 1, 1, 9.0f, 1, now, now, ""))

        val orderDto = OrderDto(1, 1, "", 1, "", 9.0f, 1, now, now, "")
        val modifyOrder = orderServiceImpl.modifyOrder(orderDto)
        Assertions.assertEquals("soccer ball", modifyOrder.itemName)
        Assertions.assertEquals("Huang", modifyOrder.userLastName)

    }
}