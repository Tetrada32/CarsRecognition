package com.car_plate.data.repository.event


//class EventRepositoryImpl(
//    private val eventRemoteSource: EventRemoteSource,
//    private val apiEventMapper: ApiMapper<ApiEventList, List<Event>, Map<String, Any>>,
//    private val apiEventDetailsMapper: ApiMapper<ApiEvent, EventDetails, Map<String, Any>>,
//    private val apiEventItemDetailsMapper: ApiMapper<ApiItem, EventItemDetails, Map<String, Any>>
//) : EventRepository {
//
//
//    override suspend fun getEventsList(): UseCaseResult<List<Event>> =
//        try {
//            val eventData = eventRemoteSource.getEventsList()
//            UseCaseResult.Success(apiEventMapper.toDomain(eventData))
//        } catch (error: Throwable) {
//           UseCaseResult.Error(error)
//        }
//
//    override suspend fun getEventDetails(eventId: Long): UseCaseResult<EventDetails> = try {
//        val eventData = eventRemoteSource.getEventDetails(eventId)
//        UseCaseResult.Success(apiEventDetailsMapper.toDomain(eventData))
//    } catch (error: Throwable) {
//        UseCaseResult.Error(error)
//    }
//
//    override suspend fun getEventItemDetails(
//        itemId: Long
//    ): UseCaseResult<EventItemDetails> = try {
//        val apiItem = eventRemoteSource.getEventItemDetails(itemId)
//        UseCaseResult.Success(
//            apiEventItemDetailsMapper.toDomain(apiItem)
//        )
//    } catch (error: Throwable) {
//        UseCaseResult.Error(error)
//    }
//
//}