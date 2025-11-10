package com.ah.wallpapers.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ah.wallpapers.AppContainer
import com.ah.wallpapers.data.WallpaperCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = true,
    val categories: List<WallpaperCategory> = emptyList(),
    val selectIndex: Int = 0,
)

class HomeViewModel : ViewModel() {
    private val wallpaperRepository = AppContainer.wallpaperRepository
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            wallpaperRepository.getWallpapers()
                .onSuccess { data -> _uiState.update { it.copy(isLoading = false, categories = data) } }
                .onFailure { _uiState.update { it.copy(isLoading = false) } }
        }
    }

    fun onSelectIndex(index: Int) {
        _uiState.update { it.copy(selectIndex = index) }
    }
}