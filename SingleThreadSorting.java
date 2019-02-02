package net.ukr.andy777;

public class SingleThreadSorting implements Runnable {
	private int[] array;
	private int begin;
	private int end;
	private int sortMethod; // метод сортування, що буде застосовано
	private Thread thr;
	private int index; // індекс першого невикористаного елементу вопрядкованого масиву в результуючому масиві
	private boolean stop = false; // чи використані всі елементи відсортованого потоку в результуючому масиві

	// thread constructor = конструктор потоку
	public SingleThreadSorting(int[] array, int begin, int end, int sortMethod) {
		super();
		this.array = array;
		this.begin = begin;
		this.end = end;
		this.sortMethod = sortMethod;
		thr = new Thread(this); // ініціалізація потоку
		thr.start(); // запуск потоку
		this.index = begin;
	}

	public Thread getThr() {
		return thr;
	}

	public boolean isStop() {
		return stop;
	}

	// перший елемент вопрядкованого масиву потоку, який ще не пішов в результуючий загальний вопрядкований масив
	public int peekElement() {
		return array[index];
	}

	// відсилка першого вільного елементу вопрядкованого масиву потоку до результуючого загального вопрядкованого масиву
	public int pollElement() {
		int temp = array[index];
		check();
		return temp;
	}

	// перевірка на використання всіх елементів вопрядкованого масиву потоку в результуючому загальному вопрядкованому
	// масиві
	private void check() {
		this.index++;
		if (this.index >= this.end) {
			this.stop = true;
		}
	}

	@Override
	public void run() {// реалізація інтерфейсу Runnable для багатопотоковості 
		switch (this.sortMethod) {
		case 0: // Bubble
			sort_Bubble(array, begin, end);
			break;
		case 1: // Insert
			sort_Insert(array, begin, end);
			break;
		case 2: // Shell
			sort_Shell(array, begin, end);
			break;
		case 3: // Shell2
			sort_Shell2(array, begin, end);
			break;
		default:
			break;
		}
	}

	/**
	 * Modified SHELL method of sort range of Array from Begin-element to End-element, not included = модифікований
	 * метод сортування ШЕЛЛА діапазону масиву від Початкового-елемента до Кінцевого-елемента, не включно
	 * 
	 * @param array
	 *            <code>int[]</code> array for sorting
	 * @param begin
	 *            <code>int</code> first array-element for sorting
	 * @param end
	 *            <code>int</code> afterlast array-element for sorting
	 * @return int[] sorted array.
	 * @author ap
	 */
	private int[] sort_Shell2(int[] array, int begin, int end) {
		int[] d = { 1, 4, 10, 23, 57, 145, 356, 911, 1968, 4711, 11969, 27901, 84801, 213331, 543749, 1355339, 3501671,
				8810089, 21521774, 58548857, 157840433, 410151271, 1131376761, 2147483647 };
		int m = 0;
		for (; d[m] < (end - begin); m++)
			;
		for (; --m >= 0;) {
			int k = d[m];
			for (int i = begin + k; i < end; i++) {
				int j = i;
				int temp = array[i];
				for (; (j - begin) >= k && array[j - k] > temp; j -= k)
					array[j] = array[j - k];
				array[j] = temp;
			}
		}
		return array;
	}

	/**
	 * Classic SHELL method of sort range of Array from Begin-element to End-element, not included = класичний метод
	 * сортування ШЕЛЛА діапазону масиву від Початкового-елемента до Кінцевого-елемента, не включно
	 * 
	 * @param array
	 *            <code>int[]</code> array for sorting
	 * @param begin
	 *            <code>int</code> first array-element for sorting
	 * @param end
	 *            <code>int</code> afterlast array-element for sorting
	 * @return int[] sorted array.
	 * @author ap
	 */
	private int[] sort_Shell(int[] array, int begin, int end) {
		for (int k = (end - begin) / 2; k > 0; k /= 2)
			for (int i = begin + k; i < end; i++)
				for (int j = i; (j - begin) >= k && array[j - k] > array[j]; j -= k)
					swap(array, j, j - k);
		return array;
	}

	/**
	 * INSERT method of sort range of Array from Begin-element to End-element, not included = метод сортування ВСТАВКОЮ
	 * діапазону масиву від Початкового-елемента до Кінцевого-елемента, не включно
	 * 
	 * @param array
	 *            <code>int[]</code> array for sorting
	 * @param begin
	 *            <code>int</code> first array-element for sorting
	 * @param end
	 *            <code>int</code> afterlast array-element for sorting
	 * @return int[] sorted array.
	 * @author ap
	 */
	private int[] sort_Insert(int[] array, int begin, int end) {
		int temp;
		for (int i = begin; i < end; i++) {
			int k = i - 1;
			temp = array[i];
			for (; k >= begin && array[k] > temp;) {
				array[k + 1] = array[k];
				array[k] = temp;
				k--;
			}
		}
		return array;
	}

	/**
	 * BUBBLE method of sort range of Array from Begin-element to End-element, not included = метод БУЛЬБАШКОВОГО
	 * сортування діапазону масиву від Початкового-елемента до Кінцевого-елемента, не включно
	 * 
	 * @param array
	 *            <code>int[]</code> array for sorting
	 * @param begin
	 *            <code>int</code> first array-element for sorting
	 * @param end
	 *            <code>int</code> afterlast array-element for sorting
	 * @return int[] sorted array.
	 * @author ap
	 */
	private int[] sort_Bubble(int[] array, int begin, int end) {
		boolean isSorted = false;
		while (!isSorted) {
			isSorted = true;
			for (int i = begin; i < end - 1; i++) {
				if (array[i] > array[i + 1]) {
					isSorted = false;
					// System.out.println((i)+"<->"+(i+1));
					swap(array, i, i + 1);
				}
			}
		}
		return array;
	}

	/**
	 * Swap elements of integer array = обмін елементів цілочисельного масиву
	 * 
	 * @param arr
	 *            <code>int[]</code> array for swaping
	 * @param i
	 *            <code>int</code> array-element for swapping
	 * @param j
	 *            <code>int</code> array-element for swapping
	 * @return int[] swaped array.
	 * @author ap
	 */
	public static int[] swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
		return arr;
	}

}
