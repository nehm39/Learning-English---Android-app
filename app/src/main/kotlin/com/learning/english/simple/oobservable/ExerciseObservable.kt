package com.learning.english.simple.oobservable

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.TypedValue
import rx.Observable

object ExerciseObservable {
    fun loadExerciseImage(resources: Resources, currentWord: String) : Observable<Drawable> {
        return Observable.create(Observable.OnSubscribe<Drawable> { subscriber ->
            val resourceImage = Drawable.createFromResourceStream(resources, TypedValue(), resources.getAssets().open("exercises/" + currentWord + ".jpg"), null)
            subscriber.onNext(resourceImage)
            subscriber.onCompleted()
        })
    }
}