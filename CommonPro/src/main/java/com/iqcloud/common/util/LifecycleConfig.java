package com.iqcloud.common.util;

public class LifecycleConfig
{
  private static int lifecycle = 0;

  public static int getLifecycle()
  {
    if (lifecycle == 0)
    {
      String lifecyclesString = LifecycleConfigHolder.lifecycle;
      if (lifecyclesString != null) {
        try {
          lifecycle = Integer.parseInt(lifecyclesString);
        } catch (NumberFormatException e) {
          lifecycle = 0;
        }
      }

    }

    return lifecycle;
  }

  private static class LifecycleConfigHolder
  {
    private static String lifecycle = FileControl.getPath("main", "lifecycle");
  }
}