package com.damianogiusti.statedispatcher.internal

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.damianogiusti.statedispatcher.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.*
import kotlin.reflect.KClass

/**
 * Created by Damiano Giusti on 23/04/18.
 */

internal class StateDispatcherImpl(
    application: Application,
    private val screensAndLayouts: Map<KClass<out ScreenState>, Int>,
    private val screenCreatorFactory: ScreenCreatorFactory
) : StateDispatcher, KtActivityLifecycleCallbacks {

    init {
        application.registerActivityLifecycleCallbacks(this)
    }

    private var screenCreator: ScreenCreator? = null
    private val backStack: Stack<ScreenState> = Stack()
    private val eventsSubject: Subject<NavigationState> = PublishSubject.create<NavigationState>()

    override val latest: NavigationState?
        get() = backStack.takeIf { it.isNotEmpty() }?.peek()

    override fun dispatch(state: NavigationState) {
        when (state) {
            is NavigationState.Show -> {
                // Hide the topmost screen if the new state is not the same as the current.
                if (backStack.isNotEmpty() && backStack.peek() != state.screen) {
                    post(NavigationState.Hide(backStack.peek()))
                }
                // Push the state to the back stack only if the screen to show
                // is not the same as the topmost one.
                if (backStack.isEmpty() || backStack.peek() != state.screen) {
                    backStack.push(state.screen)
                }
                // Dispatch the state.
                post(state)
            }
            is NavigationState.GoBack -> {
                // Remove the topmost screen and post a hide event for it.
                val stateToHide = backStack.pop()
                val shouldHide = backStack.isNotEmpty()
                if (shouldHide) {
                    post(NavigationState.Hide(stateToHide))
                }

                if (backStack.isEmpty()) {
                    // If the back stack is empty, post an exit event to terminate the app.
                    post(NavigationState.Exit)
                } else {
                    // Dispatch the state for the new topmost screen.
                    dispatch(backStack.peek())
                }
            }
            is ScreenState -> {
                // Create a new screen and dispatch a show event for the created state.
                screenCreator?.create(state)?.let { createdScreen -> dispatch(NavigationState.Show(createdScreen)) }
            }
        }
    }

    override fun goBack() {
        // The back stack is not empty, so post a go-back state.
        dispatch(NavigationState.GoBack)
    }

    override fun showEvents(screenType: KClass<out ScreenState>): Flowable<NavigationState.Show> {
        return eventsSubject
            .filter { it is NavigationState.Show }
            .cast(NavigationState.Show::class.java)
            .filter { it.screen::class == screenType }
            .toFlowable(BackpressureStrategy.LATEST)
    }

    override fun hideEvents(screenType: KClass<out ScreenState>): Flowable<NavigationState.Hide> {
        return eventsSubject
            .filter { it is NavigationState.Hide }
            .cast(NavigationState.Hide::class.java)
            .filter { it.screen::class == screenType }
            .toFlowable(BackpressureStrategy.LATEST)
    }

    override fun exitEvents(): Flowable<NavigationState.Exit> {
        return eventsSubject
            .filter { it == NavigationState.Exit }
            .cast(NavigationState.Exit::class.java)
            .toFlowable(BackpressureStrategy.LATEST)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        screenCreator = (activity as? ScreenContainer)?.let { container ->
            screenCreatorFactory.newScreenCreator(container.containerLayout, screensAndLayouts)
        }
    }

    private fun post(state: NavigationState) {
        eventsSubject.onNext(state)
    }
}