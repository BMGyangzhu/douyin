import { ref } from 'vue'

export default function useCountdown(duration = 60) {
  const countdown = ref(duration)
  const isCounting = ref(false)
  let timer = null

  const start = () => {
    if (isCounting.value) return

    countdown.value = duration
    isCounting.value = true

    timer = setInterval(() => {
      countdown.value--
      if (countdown.value <= 0) {
        clearInterval(timer)
        isCounting.value = false
      }
    }, 1000)
  }

  const reset = () => {
    clearInterval(timer)
    isCounting.value = false
    countdown.value = duration
  }

  return {
    countdown,
    isCounting,
    start,
    reset
  }
}
