/*
 * Copyright (C) 23. 2. 9. 오후 1:16 Ahngbeom (https://github.com/Ahngbeom)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.perfect.community.api.mapper.board;

import com.perfect.community.api.mapper.MapperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@DisplayName("[Mapper] Read board")
class ReadTest extends MapperTest {

    @Test
    @DisplayName("Get board list.")
    void getBoardList() {
        log.info(boardMapper.getBoardList());
    }

    @Test
    @DisplayName("Get board info with valid board no.")
    void getBoardInfo() {
        log.info(boardMapper.getBoardInfo(1));
    }

    @Test
    @DisplayName("Get board info with invalid board no.")
    void getBoardInfoWithInvalidBoardNo() {
        try {
            log.info(boardMapper.getBoardInfo(-1));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}