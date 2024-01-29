package com.colddelight.data.model

import com.colddelight.model.Manda
import com.colddelight.model.Todo

sealed class SetAction {

    data class DelManda(val id: Int) : SetAction()
    data class DelTodo(val id: Int) : SetAction()
    data class InsertManda(val manda: Manda) : SetAction()
    data class InsertTodo(val todo: Todo) : SetAction()
}





