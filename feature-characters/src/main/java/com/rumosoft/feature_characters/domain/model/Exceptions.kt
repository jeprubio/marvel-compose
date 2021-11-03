package com.rumosoft.feature_characters.domain.model

import java.io.IOException

class CallInProgressException(message: String) : ConcurrentModificationException(message)

class NoMoreResultsException(message: String) : ArrayIndexOutOfBoundsException(message)

class NetworkErrorException(message: String) : IOException(message)
