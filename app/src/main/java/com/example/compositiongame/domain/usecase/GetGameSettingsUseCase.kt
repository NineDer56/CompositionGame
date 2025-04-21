package com.example.compositiongame.domain.usecase

import com.example.compositiongame.domain.entity.GameSettings
import com.example.compositiongame.domain.entity.Level
import com.example.compositiongame.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level) : GameSettings{
        return repository.getGameSettings(level)
    }
}