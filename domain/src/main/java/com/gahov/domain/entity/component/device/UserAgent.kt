package com.gahov.domain.entity.component.device

class UserAgent(deviceInfo: DeviceInfo) : UserAgentProvider {
    override val userAgent: String = String.format(
        PATTERN,
        deviceInfo.versionName,
        deviceInfo.versionCode,
        deviceInfo.manufacturer,
        deviceInfo.model,
        deviceInfo.systemVersion
    )

    companion object {
        private const val PATTERN = "%s/%s (Device: %s %s; Android %s)"
    }
}