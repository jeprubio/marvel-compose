package com.rumosoft.commons.domain.model

import java.io.IOException

class CallInProgressException(message: String) : ConcurrentModificationException(message)

class NetworkErrorException(message: String) : IOException(message)
