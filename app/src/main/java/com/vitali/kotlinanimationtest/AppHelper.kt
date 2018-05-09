package com.vitali.kotlinanimationtest

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.CredentialsOptions

fun Context.credentialsApiClient() = Credentials.getClient(this, CredentialsOptions.Builder().forceEnableSaveDialog().build())

fun FragmentActivity.findFragmentById(tag: Int): Fragment? = supportFragmentManager.findFragmentById(tag)

fun FragmentActivity.findFragmentByTag(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)