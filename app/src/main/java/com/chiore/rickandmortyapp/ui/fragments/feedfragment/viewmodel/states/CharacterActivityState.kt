package com.chiore.rickandmortyapp.ui.fragments.feedfragment.viewmodel.states

data class CharacterActivityState(
    val listType: ListType = ListType.GridLayout
)

enum class ListType() {
    LinearLayout(),
    GridLayout()
}
