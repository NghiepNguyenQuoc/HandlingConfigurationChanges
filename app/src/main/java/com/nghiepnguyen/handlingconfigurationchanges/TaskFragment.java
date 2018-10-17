package com.nghiepnguyen.handlingconfigurationchanges;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class TaskFragment extends Fragment {
    interface TaskCallback {
        void onPreExecute();

        void onProgressUpdate(int progress);

        void onCancelled();

        void onPostExecute();
    }

    private TaskCallback mCallback;
    private DummyTask dummyTask;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (TaskCallback) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
        dummyTask = new DummyTask();
        dummyTask.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    private class DummyTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mCallback != null)
                mCallback.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; !isCancelled() && i < 100; i++) {
                SystemClock.sleep(100);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (mCallback != null)
                mCallback.onProgressUpdate(values[0]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (mCallback != null)
                mCallback.onCancelled();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mCallback != null)
                mCallback.onPostExecute();
        }
    }
}
