/*
 * Copyright (C) 2017 The Nitrogen Project
 * Copyright (C) 2017 The Liquid Remix Project
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

package com.android.settings.arsenic;

import android.os.Bundle;
import android.os.UserHandle;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;

import com.android.settings.R;
import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.SettingsPreferenceFragment;

public class SoundSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String HEADSET_CONNECT_PLAYER = "headset_connect_player";

    private ListPreference mLaunchPlayerHeadsetConnection;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.arsenic_sound_settings);

        // Launch music player on headset connect

        mLaunchPlayerHeadsetConnection = (ListPreference) findPreference(HEADSET_CONNECT_PLAYER);
        int mLaunchPlayerHeadsetConnectionValue = Settings.System.getIntForUser(getContentResolver(),
                Settings.System.HEADSET_CONNECT_PLAYER, 4, UserHandle.USER_CURRENT);
        mLaunchPlayerHeadsetConnection.setValue(Integer.toString(mLaunchPlayerHeadsetConnectionValue));
        mLaunchPlayerHeadsetConnection.setSummary(mLaunchPlayerHeadsetConnection.getEntry());
        mLaunchPlayerHeadsetConnection.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        if (preference == mLaunchPlayerHeadsetConnection) {
            int mLaunchPlayerHeadsetConnectionValue = Integer.valueOf((String) value);
            int index = mLaunchPlayerHeadsetConnection.findIndexOfValue((String) value);
            mLaunchPlayerHeadsetConnection.setSummary(
                    mLaunchPlayerHeadsetConnection.getEntries()[index]);
            Settings.System.putIntForUser(getActivity().getContentResolver(),
            	    Settings.System.HEADSET_CONNECT_PLAYER, mLaunchPlayerHeadsetConnectionValue, UserHandle.USER_CURRENT);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.ARSENIC;
    }
}
