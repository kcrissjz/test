/*
 * Copyright (c) 2020. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.bdpp.common.viewbindingktx

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.bdpp.common.R
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lxj.xpopup.core.BasePopupView

@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> BaseViewHolder.getBinding(bind: (View) -> VB): VB =
  itemView.getTag(R.id.tag_view_binding) as? VB ?: bind(itemView).also { itemView.setTag(R.id.tag_view_binding, it) }

//@Suppress("UNCHECKED_CAST")
//fun <VB : ViewBinding> BasePopupView.getPopupBinding(bind: (View) -> VB): VB =
//  this.getTag(R.id.tag_view_binding) as? VB ?: bind(this).also { this.setTag(R.id.tag_view_binding, it) }
