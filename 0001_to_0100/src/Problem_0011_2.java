public class Problem_0011_2 {

    public static void main(String[] args) {
        Problem_0011_2 s = new Problem_0011_2();
        int[] in1 = {1, 8, 6, 2, 5, 4, 8, 3, 7}; // Out: 49
        int[] in2 = {1, 1}; // Out: 1
        System.out.println(s.maxArea(in1));
        System.out.println(s.maxArea(in2));
    }

    public int maxArea(int[] height) {
        if(height == null || height.length == 0) {
            return 0;
        }
        int start = 0;
        int end = height.length - 1;
        int maxArea = 0;
        while(start < end) {
            int dimH = end - start;
            int dimV = Math.min(height[start], height[end]);
            int area = dimH * dimV;
            if (area > maxArea) {
                maxArea = area;
                System.out.printf("New max area set: %d\n", maxArea);
            }

            if (height[start] < height[end]) {
                start++;
            } else {
                end--;
            }
        }

        return maxArea;
        }
}

/*
The widest rect gives the most area (so start from 0 to n-1)
However, a narrower rect can give a bigget area (if the height is much bigger, so keep narrowing on the smaller side
if there's a bigger rect)

Consider rect like |||||....    ....||||||.     ....||||.....       ....||||...jjjj     ....||||...iii|i

In the first two, we can simply find the largest in 0(n) time by narrowing from the smaller edge while keeping a max value

Same will work for 3 when either side reaches the ||||s and forces the other side to reach there

4th answer is unclear it can be |||| or ||||....iiii depending on actual values. However, since we go through both
combinations (i.e., we first reach llll....iiii, then llll....i and then llll), we will actually find the biggest

5th answer is a little more complex. Is the right solution llll or ||||....iiii|
While going through combinations, we'll reach the latter first, and then we go to the former anyway

So, this approach actually goes through all combinations that we want it to. Proceeding with alg


Alg:
// Skip null, empty or 1-ele check.
// No 2-ele check necessary. It runs through the while Loop once
int start = a[0]
int end = a[a.len - 1]
int maxArea = 0
while (start < end)
    int width = end - start
    int height = Math.min(a[start], a[end]
    area = width * height

    if (area > maxArea)
        maxArea = area

    if (a[start] < a[end])
        start++
     else
        end--

return maxArea

 */