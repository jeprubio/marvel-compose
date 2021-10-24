package com.rumosoft.marvelcompose.domain.model

class NoMoreResultsException(message: String) : ArrayIndexOutOfBoundsException(message)

class CallInProgressException(message: String) : ConcurrentModificationException(message)
