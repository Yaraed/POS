/*
 *
 *  Copyright 2017 liu-feng
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  imitations under the License.
 *
 */

package com.weyee.sdk.player.record;


import com.weyee.sdk.player.entity.DataSource;

/**
 * Created by Taurus on 2018/12/12.
 *
 * if you want to custom save record, you can set it.
 *
 */
public interface OnRecordCallBack {

    int onSaveRecord(DataSource dataSource, int record);

    int onGetRecord(DataSource dataSource);

    int onResetRecord(DataSource dataSource);

    int onRemoveRecord(DataSource dataSource);

    void onClearRecord();

}