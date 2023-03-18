package com.paradise.source_code.process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * container for business pojo
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PostContext<T> {

    private T bizData;
}
