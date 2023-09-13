package solutions;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.PriorityQueue;
import java.util.Queue;

public class CookiesProblem {
    public Integer solve(int k, int[] cookies) {
        Queue<Integer> queue = new PriorityQueue<>();

        for (int cookie : cookies) {
            queue.add(cookie);
        }

        int currentMinSweetness = queue.peek();

        int steps = 0;

        while (currentMinSweetness < k && queue.size() > 1){
            int leastSweetCookies = queue.poll();
            int secondLeastCookie = queue.poll();

            int combinedSweetness = leastSweetCookies + 2 * secondLeastCookie;

            queue.add(combinedSweetness);

            currentMinSweetness = queue.peek();
            steps++;
        }

        return currentMinSweetness > k ? steps : -1;
    }

}
