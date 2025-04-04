package com.example.englishwordsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class LifecycleFragment : Fragment() {

    private lateinit var tvLifecycleLog: TextView

    private fun logEvent(event: String) {
        tvLifecycleLog.append("\n$event")
    }

    fun publicLogEvent(event: String) {
        logEvent(event)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lifecycle, container, false)
        tvLifecycleLog = view.findViewById(R.id.tvLifecycleLog)
        logEvent("onCreateView")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logEvent("onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        logEvent("onStart")
    }

    override fun onResume() {
        super.onResume()
        logEvent("onResume")
    }

    override fun onPause() {
        logEvent("onPause")
        super.onPause()
    }

    override fun onStop() {
        logEvent("onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        logEvent("onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        logEvent("onDestroy")
        super.onDestroy()
    }
}
