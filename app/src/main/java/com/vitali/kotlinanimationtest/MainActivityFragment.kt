package com.vitali.kotlinanimationtest

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.common.api.ResolvableApiException
import kotlinx.android.synthetic.main.fragment_main.*

const val REQUEST_LOGIN = 704

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment()
{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        tv_click_me.setOnClickListener{
            Toast.makeText(it.context, "Click !",Toast.LENGTH_SHORT).show()

            it.context.credentialsApiClient()
                    .request(CredentialRequest.Builder().setPasswordLoginSupported(true).build())
                    .addOnCompleteListener{
                        if(it.isSuccessful)
                        {
                            println(it.result.credential)
                        }
                        else
                        {
                            resolveException(it.exception, REQUEST_LOGIN)
                        }
                    }
        }
    }

    /**
     * Resolve exceptions throughout different processes
     */
    private fun resolveException(exception: Exception?, requestCode: Int) {
        if (exception is ResolvableApiException) {
            Log.d(ContentValues.TAG, "Resolving: $exception")
            try {
                exception.startResolutionForResult(activity, requestCode)
            } catch (e: IntentSender.SendIntentException) {
                Log.e(ContentValues.TAG, "STATUS: Failed to send resolution.", e)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        onSmartLockResult(
                requestCode,
                resultCode,
                data,
                success = {
                    Toast.makeText(activity, "Success ! "+ it.id ,Toast.LENGTH_SHORT).show()


                    /*player = it*/
                    /*initContents()*/
                    /*navigateToCategoryActivity()*/
                },
                failure = {
                    /*activity?.run {
                        login.loginPlayer(this, ::onSuccessfulLogin)

                    }*/

                    Toast.makeText(activity, "Failure !",Toast.LENGTH_SHORT).show()
                }
        )
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Handles the activity result logic when a call to the SmartLock API is made.
     * Hook this into onActivityResult for maximum benefit.
     */
    private fun onSmartLockResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?,
            success: (/*Player*/Credential) -> Unit,
            failure: () -> Unit) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_LOGIN ->
            {
                data?.run {
                    if (hasExtra(Credential.EXTRA_KEY))
                    {
                        /*val player = Player(getParcelableExtra(Credential.EXTRA_KEY)).also {
                            storePlayerLocally(it)
                        }*/

                        val credential: Credential = getParcelableExtra(Credential.EXTRA_KEY)
                        success(/*player*/credential)
                    }
                    else
                    {
                        failure()
                    }
                }
            }
            /*REQUEST_SAVE -> (Log.d(ContentValues.TAG, "savePlayer result"))*/
        }
    }
}
